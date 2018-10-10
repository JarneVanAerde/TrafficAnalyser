package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlates.LicensePlateInfoDTO;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
import be.kdg.processor.services.impl.modelservices.CameraMessageService;
import be.kdg.processor.services.impl.adapters.LicensePlateInfoService;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import be.kdg.sa.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    private final CameraMessageService cameraMessageService;
    private final VehicleService licensePlateService;

    @Autowired
    public EmissonDetectionService(CameraInfoService cameraInfoService, LicensePlateInfoService licensePlateInfoService,
                                   FineService fineService, CameraMessageService cameraMessageService, VehicleService licensePlateService) {
        this.cameraInfoService = cameraInfoService;
        this.licensePlateInfoService = licensePlateInfoService;
        this.fineService = fineService;
        this.cameraMessageService = cameraMessageService;
        this.licensePlateService = licensePlateService;
    }

    /**
     * The external service are behind an adapter and are used to
     * determine the fines.
     * If the message can't be linked to a fine, then it is
     * saved to the database.
     *
     * @param message the message that will be used to detect possible emission fines.
     * @throws IOException is thrown when a communication error occurs.
     * @throws LicensePlateNotFoundException is thrown when a license plate wasn't found in the external database.
     * @throws CameraNotFoundException is thrown when a camera wasn't found in the external database.
     * @throws InvalidLicensePlateException is thrown when a license plate was invalid.
     */
    @Override
    public void detectFine(CameraMessage message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException, InvalidLicensePlateException {
        //Call adapter
        Camera camera = cameraInfoService.get(message.getId());
        LicensePlateInfoDTO licensePlateInfo = licensePlateInfoService.get(message.getLicensePlate());

        //Detect fine
        if (camera.getEuroNorm() > licensePlateInfo.getEuroNumber()) {
            LOGGER.info("Fine detected for " + licensePlateInfo.getPlateId() + " on camera " + camera.getCameraId() + ".");
            licensePlateService.extractPlateInfo(licensePlateInfo);
            fineService.createEmissionFine(calculateFine(camera.getEuroNorm(), licensePlateInfo.getEuroNumber()),
                    licensePlateInfo.getEuroNumber(), camera.getEuroNorm(), message, licensePlateInfo.getPlateId());
        } //else cameraMessageService.createCameraMessage(message);
    }

    /**
     * Calculates the difference between the two euro numbers
     * and multiples that difference times 100.
     *
     * @param legalEurNumber the legal euro number of the area.
     * @param vehicleEuroNumber the euro number of the vehicle.
     * @return the calculated fine.
     */
    private double calculateFine(int legalEurNumber, int vehicleEuroNumber) {
        return (legalEurNumber - vehicleEuroNumber) * 100;
    }
}
