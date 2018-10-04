package be.kdg.processor.services;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlateInfos.LicensePlateInfo;
import be.kdg.sa.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmmissonDetectionService implements DetectionService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmmissonDetectionService.class);
    private final CameraServiceProxy cameraServiceProxy;
    private final LicensePlateServiceProxy licensePlateServiceProxy;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmmissonDetectionService(CameraServiceProxy cameraServiceProxy, LicensePlateServiceProxy licensePlateServiceProxy, ObjectMapper objectMapper) {
        this.cameraServiceProxy = cameraServiceProxy;
        this.licensePlateServiceProxy = licensePlateServiceProxy;
        this.objectMapper = objectMapper;
    }

    @Override
    public void detectFine(CameraMessage message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException, InvalidLicensePlateException {
        //Extract JSON
        String cameraJson = cameraServiceProxy.get(message.getId());
        String licenseJson = licensePlateServiceProxy.get(message.getLicensePlate());
        Camera camera = objectMapper.readValue(cameraJson, Camera.class);
        LicensePlateInfo licensePlateInfo = objectMapper.readValue(licenseJson, LicensePlateInfo.class);

        //Detect fine
        if (camera.getEuroNorm() > licensePlateInfo.getEuroNumber())
            LOGGER.info("Fine detected for " + licensePlateInfo.getPlateId() + " on camera " + camera.getCameraId() + ".");
    }
}
