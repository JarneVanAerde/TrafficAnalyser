package be.kdg.processor.services;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeedfineDetectionService implements DetectionService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedfineDetectionService.class);
    private final CameraServiceProxy cameraServiceProxy;
    private final LicensePlateServiceProxy licensePlateServiceProxy;
    private final ObjectMapper objectMapper;

    @Autowired
    public SpeedfineDetectionService(CameraServiceProxy cameraServiceProxy, LicensePlateServiceProxy licensePlateServiceProxy, ObjectMapper objectMapper) {
        this.cameraServiceProxy = cameraServiceProxy;
        this.licensePlateServiceProxy = licensePlateServiceProxy;
        this.objectMapper = objectMapper;
    }

    @Override
    public void detectFine(CameraMessage message) {
       //TODO: detect speedfine
    }
}
