package be.kdg.processor.services;

import be.kdg.processor.config.ExternalServiceConfig;
import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlateInfos.LicensePlateInfo;
import be.kdg.processor.repositories.CameraDAO;
import be.kdg.processor.services.parsingServices.JSONService;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmmissonDetectionService implements DetectionService<CameraMessage> {
    private final CameraServiceProxy cameraServiceProxy;
    private final LicensePlateServiceProxy licensePlateServiceProxy;
    private final CameraDAO cameraDAO;

    @Autowired
    public EmmissonDetectionService(CameraServiceProxy cameraServiceProxy, LicensePlateServiceProxy licensePlateServiceProxy, CameraDAO cameraDAO) {
        this.cameraServiceProxy = cameraServiceProxy;
        this.licensePlateServiceProxy = licensePlateServiceProxy;
        this.cameraDAO = cameraDAO;
    }

    @Override
    public void detectFine(CameraMessage message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException {
        String cameraJson = cameraServiceProxy.get(message.getId());
        Camera camera = (Camera) JSONService.unmarshal(cameraJson, Camera.class);
        String licenseJson = licensePlateServiceProxy.get(message.getLicensePlate());
        LicensePlateInfo licensePlateInfo = (LicensePlateInfo) JSONService.unmarshal(licenseJson, LicensePlateInfo.class);

        if (camera.getEuroNorm() > licensePlateInfo.getEuroNumber())
            System.out.println("fine detected for " + licensePlateInfo.getPlateId() +  " on camera " + camera.getCameraId());
    }
}
