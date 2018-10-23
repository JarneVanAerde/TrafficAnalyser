package be.kdg.processor.services.impl.detections;

import be.kdg.processor.config.RetryConfig;
import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
import be.kdg.processor.services.impl.adapters.LicensePlateInfoService;
import be.kdg.processor.services.impl.modelservices.FineService;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * This class is used for the detection of illegal emissions.
 * All messages will pass through here to check for illegal emissions.
 */
@Service
public class EmissionFineDetectionService implements DetectionService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionFineDetectionService.class);

    private final CameraInfoService cameraInfoService;
    private final LicensePlateInfoService licensePlateInfoService;
    private final FineService fineService;
    private final VehicleService vehicleService;

    @Autowired
    public EmissionFineDetectionService(CameraInfoService cameraInfoService, LicensePlateInfoService licensePlateInfoService,
                                        FineService fineService, VehicleService licensePlateService) {
        this.cameraInfoService = cameraInfoService;
        this.licensePlateInfoService = licensePlateInfoService;
        this.fineService = fineService;
        this.vehicleService = licensePlateService;
    }

    /**
     * The external services are behind an adapter and are used to
     * determine the fines.
     * If the message can't be linked to a fine, then it is
     * thrown away.
     *
     * If the proxy's throw an exception, then a retry mechanism will go back
     * and retry several times
     *
     * If a emission fine is dedected, then it will be passed to the fineService.
     *
     * @param message the message that will be used to detect possible emission fines.
     */
    @Override
    public void detectFine(CameraMessage message) throws ServiceException {
        //Get retry template
        RetryTemplate retryTemplate = RetryConfig.retryTemplate;

        //Call adapters with retry
        Camera camera =
                retryTemplate.execute(context -> cameraInfoService.get(message.getCameraId()));
        LicensePlateInfo licensePlateInfo =
                retryTemplate.execute(context -> licensePlateInfoService.get(message.getLicensePlate()));

        //Detect fine & extract plate info
        if (camera.getEuroNorm() > licensePlateInfo.getEuroNumber()) {
            vehicleService.extractPlateInfo(licensePlateInfo);

            if (!fineService.checkIfAlreadyHasEmissionfine(licensePlateInfo.getPlateId())) {
                LOGGER.info("Emission Fine detected for " + licensePlateInfo.getPlateId() + " on camera " + camera.getCameraId() + ".");
                fineService.createEmissionFine(licensePlateInfo.getEuroNumber(), camera.getEuroNorm(),
                        message, licensePlateInfo.getPlateId());
            }
        }
    }
}
