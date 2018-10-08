package be.kdg.processor.services.impl;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.CameraServiceAdapter;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.api.LicensePlateServiceAdatpter;
import be.kdg.sa.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
     * @throws IOException is thrown when a communication error occurs.
     * @throws LicensePlateNotFoundException is thrown when a license plate wasn't found in the external database.
     * @throws CameraNotFoundException is thrown when a camera wasn't found in the external database.
     * @throws InvalidLicensePlateException is thrown when a license plate was invalid.
     */
    @Override
    public void detectFine(CameraMessage message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException, InvalidLicensePlateException {
        //Call adapter
        Camera camera = cameraInfoService.get(message.getId());
        LicensePlateInfo licensePlateInfo = licensePlateInfoService.get(message.getLicensePlate());

        //Detect fine
        //TODO: detect speedfine
    }

    private double calculateFine() {
        //TODO: calculate fine
        return 1000.0;
    }

    private double calculateSpeed() {
        //TODO: calculate speed
        return 100.0;
    }
}
