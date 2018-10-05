package be.kdg.processor.services.impl;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.CameraServiceAdapter;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.api.LicensePlateServiceAdatpter;
import be.kdg.sa.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmissonDetectionService implements DetectionService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissonDetectionService.class);
    private final CameraServiceAdapter cameraServiceAdapter;
    private final LicensePlateServiceAdatpter licensePlateServiceAdatpter;
    private final FineService fineService;

    @Autowired
    public EmissonDetectionService(CameraServiceAdapter cameraServiceAdapter, LicensePlateServiceAdatpter licensePlateServiceAdatpter, FineService fineService) {
        this.cameraServiceAdapter = cameraServiceAdapter;
        this.licensePlateServiceAdatpter = licensePlateServiceAdatpter;
        this.fineService = fineService;
    }

    @Override
    public void detectFine(CameraMessage message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException, InvalidLicensePlateException {
        //Call adapter
        Camera camera = cameraServiceAdapter.get(message.getId());
        LicensePlateInfo licensePlateInfo = licensePlateServiceAdatpter.get(message.getLicensePlate());

        //Detect fine
        if (camera.getEuroNorm() > licensePlateInfo.getEuroNumber()) {
            LOGGER.info("Fine detected for " + licensePlateInfo.getPlateId() + " on camera " + camera.getCameraId() + ".");
            fineService.createEmissionFine(calculateFine(camera.getEuroNorm(), licensePlateInfo.getEuroNumber()),
                    licensePlateInfo.getEuroNumber(), camera.getEuroNorm(), message);
        }
    }

    private double calculateFine(int legalEurNumber, int vehicleEuroNumber) {
        return (legalEurNumber - vehicleEuroNumber) * 100;
    }
}
