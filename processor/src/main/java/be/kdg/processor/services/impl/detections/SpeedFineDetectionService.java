package be.kdg.processor.services.impl.detections;

import be.kdg.processor.config.RetryConfig;
import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.cameras.Segment;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
import be.kdg.processor.services.impl.modelservices.CameraMessageService;
import be.kdg.processor.services.impl.adapters.LicensePlateInfoService;
import be.kdg.processor.services.impl.modelservices.FineService;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

/**
 * This class is used for the detection of illegal speed.
 * All messages will pass through here to check for illegal speed.
 */
@Service
public class SpeedFineDetectionService implements DetectionService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedFineDetectionService.class);
    private static final double SECONDS_IN_HOUR = 3600.0;
    private static final double METERS_IN_KM = 1000.0;

    private final CameraInfoService cameraInfoService;
    private final LicensePlateInfoService licensePlateInfoService;
    private final FineService fineService;
    private final CameraMessageService cameraMessageService;
    private final VehicleService vehicleService;

    @Autowired
    public SpeedFineDetectionService(CameraInfoService cameraInfoService, LicensePlateInfoService licensePlateInfoService,
                                     FineService fineService, CameraMessageService cameraMessageService, VehicleService vehicleService) {
        this.cameraInfoService = cameraInfoService;
        this.licensePlateInfoService = licensePlateInfoService;
        this.fineService = fineService;
        this.cameraMessageService = cameraMessageService;
        this.vehicleService = vehicleService;
    }

    /**
     * The external services are behind an adapter and are used to
     * determine the fines.
     * If the message can't be linked to a fine, then it is
     * added to a buffer.
     *
     * If the proxy's throw an exception, then a retry mechanism will go back
     * and retry several times.
     *
     * After that, a corresponding message will be retrieved.
     * if the corresponding messages is not present, then the message will be ignored.
     *
     * If the other message of the segment is present, then the speed will be calculated
     * if the speed of the vehicle exceeds the legal speed, then a fine will be created.
     *
     * @param message the message that will be used to detect possible emission fines.
     */
    @Override
    public void detectFine(CameraMessage message) throws ServiceException {
        //Get retry template
        RetryTemplate retryTemplate = RetryConfig.retryTemplate;

        //Call adapter
        Camera camera =
                retryTemplate.execute(context -> cameraInfoService.get(message.getCameraId()));
        LicensePlateInfo licensePlateInfo =
                retryTemplate.execute(context -> licensePlateInfoService.get(message.getLicensePlate()));

        //Collect corresponding message
        Optional<CameraMessage> optionalCameraMessage = Optional.empty();
        if (camera.getSegment() == null) {
            optionalCameraMessage = cameraMessageService.getCorrespondingMessage(licensePlateInfo.getPlateId(), camera.getCameraId());
        }

        //Detect fine & extract plate info
        if (optionalCameraMessage.isPresent()) {
            vehicleService.extractPlateInfo(licensePlateInfo);

            CameraMessage enterMessage = optionalCameraMessage.get();
            Segment segment = cameraInfoService.get(enterMessage.getCameraId()).getSegment();

            LOGGER.info("Calculate distance for " + enterMessage.getCameraId() + " and " + camera.getCameraId());
            double vehicleSpeed = calculateSpeed(segment, message, enterMessage);

            if (vehicleSpeed > segment.getSpeedLimit()) {
                LOGGER.info("Speed fine detected for " + licensePlateInfo.getPlateId() + " On camera " +
                        optionalCameraMessage.get().getCameraId() + " and " + camera.getCameraId());
                fineService.createSpeedFine(vehicleSpeed, segment.getSpeedLimit(),
                        enterMessage, message, licensePlateInfo.getPlateId());

                //Remove fined messages from buffer
                cameraMessageService.removeFromBuffer(message);
                cameraMessageService.removeFromBuffer(enterMessage);
            }
        } else {
            //Add current message to buffer
            cameraMessageService.addToBuffer(message);
        }
    }

    /**
     * Calculates the speed of the vehicle.
     *
     * @param segment the segment of the 2 messages.
     * @param exitMessage message that corresponds with vehicle leaving the segment.
     * @param enterMessage message that corresponds with vehicle entering the segment.
     * @return the speed of the vehicle.
     */
    private double calculateSpeed(Segment segment, CameraMessage exitMessage, CameraMessage enterMessage) {
        Duration duration = Duration.between(enterMessage.getTimestamp(), exitMessage.getTimestamp());
        return (segment.getDistance() / METERS_IN_KM) / (duration.getSeconds() / SECONDS_IN_HOUR);
    }
}
