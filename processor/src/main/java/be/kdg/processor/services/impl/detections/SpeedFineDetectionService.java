package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.cameras.Segment;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
import be.kdg.processor.services.impl.modelservices.CameraMessageService;
import be.kdg.processor.services.impl.adapters.LicensePlateInfoService;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

/**
 * This class is used for the detection of illegal speed.
 * All messages will pass through here to check for illegal emissions.
 */
@Service
public class SpeedFineDetectionService implements DetectionService<CameraMessage> {
    private static final double SECONDS_IN_HOUR = 3600.0;
    private static final double METERS_IN_KM = 1000.0;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedFineDetectionService.class);

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
     * saved to the database.
     *
     * @param message the message that will be used to detect possible emission fines.
     */
    @Override
    public void detectFine(CameraMessage message) throws ServiceException {
        //Call adapter
        Camera camera = cameraInfoService.get(message.getCameraId());
        LicensePlateInfo licensePlateInfo = licensePlateInfoService.get(message.getLicensePlate());

        //Collect corresponding message
        Optional<CameraMessage> optionalCameraMessage = Optional.empty();
        if (camera.getSegment() == null) {
            optionalCameraMessage = cameraMessageService.getConnectedMessageForEmptySegment(licensePlateInfo.getPlateId(), camera.getCameraId());
        }

        //detect fine
        if (optionalCameraMessage.isPresent()) {
            vehicleService.extractPlateInfo(licensePlateInfo);

            CameraMessage enterMessage = optionalCameraMessage.get();
            Segment segment;
            if (camera.getSegment() != null) segment = camera.getSegment();
            else segment = cameraInfoService.get(enterMessage.getCameraId()).getSegment();

            LOGGER.info("Calculation distance for " + enterMessage.getCameraId() + " and " + camera.getCameraId());
            double vehicleSpeed = calculateSpeed(segment, message, enterMessage);

            if (vehicleSpeed > segment.getSpeedLimit()) {
                LOGGER.info("Speed fine detected for " + licensePlateInfo.getPlateId() + " On camera " +
                        optionalCameraMessage.get().getCameraId() + " " + camera.getCameraId());
                fineService.createSpeedFine(vehicleSpeed, segment.getSpeedLimit(),
                        enterMessage, message, licensePlateInfo.getPlateId());
            }
        }

        cameraMessageService.addToBuffer(message);
    }

    private double calculateSpeed(Segment segment, CameraMessage cameraMessageOne, CameraMessage cameraMessageTwo) {
        Duration duration = Duration.between(cameraMessageTwo.getTimestamp(), cameraMessageOne.getTimestamp());
        return (segment.getDistance() / METERS_IN_KM) / (duration.getSeconds() / SECONDS_IN_HOUR);
    }
}
