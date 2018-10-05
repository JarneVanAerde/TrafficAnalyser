package be.kdg.processor.services.impl;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.sa.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpeedfineDetectionService implements DetectionService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedfineDetectionService.class);
    private final CameraServiceProxy cameraServiceProxy;
    private final LicensePlateServiceProxy licensePlateServiceProxy;
    private final ObjectMapper objectMapper;
    private final FineService fineService;

    @Autowired
    public SpeedfineDetectionService(CameraServiceProxy cameraServiceProxy, LicensePlateServiceProxy licensePlateServiceProxy, ObjectMapper objectMapper, FineService fineService) {
        this.cameraServiceProxy = cameraServiceProxy;
        this.licensePlateServiceProxy = licensePlateServiceProxy;
        this.objectMapper = objectMapper;
        this.fineService = fineService;
    }

    @Override
    public void detectFine(CameraMessage message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException, InvalidLicensePlateException {
        //Extract JSON
        String cameraJson = cameraServiceProxy.get(message.getCameraId());
        String licenseJson = licensePlateServiceProxy.get(message.getLicensePlate());
        Camera camera = objectMapper.readValue(cameraJson, Camera.class);
        LicensePlateInfo licensePlateInfo = objectMapper.readValue(licenseJson, LicensePlateInfo.class);

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
