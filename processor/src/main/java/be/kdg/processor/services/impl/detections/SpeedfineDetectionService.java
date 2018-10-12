package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlates.LicensePlateInfoDTO;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.PersistenceException;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
import be.kdg.processor.services.impl.modelservices.CameraMessageService;
import be.kdg.processor.services.impl.adapters.LicensePlateInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * This class is used for the detection of illegal speed.
 * All messages will pass through here to check for illegal emissions.
 */
@Service
public class SpeedfineDetectionService implements DetectionService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedfineDetectionService.class);
    private final CameraInfoService cameraInfoService;
    private final LicensePlateInfoService licensePlateInfoService;
    private final FineService fineService;
    private final CameraMessageService cameraMessageService;

    @Autowired
    public SpeedfineDetectionService(CameraInfoService cameraInfoService, LicensePlateInfoService licensePlateInfoService,
                                     FineService fineService, CameraMessageService cameraMessageService) {
        this.cameraInfoService = cameraInfoService;
        this.licensePlateInfoService = licensePlateInfoService;
        this.fineService = fineService;
        this.cameraMessageService = cameraMessageService;
    }

    /**
     * The external service are behind an adapter and are used to
     * determine the fines.
     * If the message can't be linked to a fine, then it is
     * saved to the database.
     *
     * @param message the message that will be used to detect possible emission fines.
     */
    @Override
    public void detectFine(CameraMessage message) throws ServiceException {
        //Call adapter
        Camera camera = cameraInfoService.get(message.getId());
        LicensePlateInfoDTO licensePlateInfo = licensePlateInfoService.get(message.getLicensePlate());

        //Detect fine
        Optional<CameraMessage> connectedMessage = Optional.empty();
        if (camera.getSegment() != null) connectedMessage = cameraMessageService.getConnectedMessage(licensePlateInfo.getPlateId(), camera.getSegment().getConnectedCameraId());
        if (connectedMessage.isPresent()) {
            double vehicleSpeed = (double) camera.getSegment().getDistance() / ChronoUnit.HOURS.between(message.getTimestamp(), connectedMessage.get().getTimestamp());
            System.out.println(vehicleSpeed);
        }
        cameraMessageService.saveCameraMessage(message);
    }

    private double calculateFine() {
        //TODO: calculate fine
        return 1000.0;
    }
}
