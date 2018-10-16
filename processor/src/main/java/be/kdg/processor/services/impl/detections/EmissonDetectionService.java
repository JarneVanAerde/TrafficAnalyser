package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.models.options.OptionKey;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
import be.kdg.processor.services.impl.adapters.LicensePlateInfoService;
import be.kdg.processor.services.impl.modelservices.OptionService;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is used for the detection of illegal emissions.
 * All messages will pass through here to check for illegal emissions.
 */
@Service
public class EmissonDetectionService implements DetectionService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissonDetectionService.class);

    private final CameraInfoService cameraInfoService;
    private final LicensePlateInfoService licensePlateInfoService;
    private final FineService fineService;
    private final VehicleService vehicleService;

    @Autowired
    public EmissonDetectionService(CameraInfoService cameraInfoService, LicensePlateInfoService licensePlateInfoService,
                                   FineService fineService, VehicleService licensePlateService) {
        this.cameraInfoService = cameraInfoService;
        this.licensePlateInfoService = licensePlateInfoService;
        this.fineService = fineService;
        this.vehicleService = licensePlateService;
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
        LicensePlateInfo licensePlateInfo = licensePlateInfoService.get(message.getLicensePlate());

        //Detect fine
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
