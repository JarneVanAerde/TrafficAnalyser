package be.kdg.processor.services;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlateInfos.LicensePlateInfo;
import be.kdg.processor.repositories.CameraDAO;
import be.kdg.processor.services.parsingServices.JSONService;
import be.kdg.sa.services.*;
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
    private final CameraDAO cameraDAO;

    @Autowired
    public EmmissonDetectionService(CameraServiceProxy cameraServiceProxy, LicensePlateServiceProxy licensePlateServiceProxy, CameraDAO cameraDAO) {
        this.cameraServiceProxy = cameraServiceProxy;
        this.licensePlateServiceProxy = licensePlateServiceProxy;
        this.cameraDAO = cameraDAO;
    }

    @Override
    public void detectFine(CameraMessage message) throws IOException, LicensePlateNotFoundException, CameraNotFoundException, InvalidLicensePlateException {
        //Extract JSON
        String cameraJson = cameraServiceProxy.get(message.getId());
        String licenseJson = licensePlateServiceProxy.get(message.getLicensePlate());
        Camera camera = (Camera) JSONService.unmarshal(cameraJson, Camera.class);
        LicensePlateInfo licensePlateInfo = (LicensePlateInfo) JSONService.unmarshal(licenseJson, LicensePlateInfo.class);

        //Detect fine
        if (camera.getEuroNorm() > licensePlateInfo.getEuroNumber())
            LOGGER.info("Fine detected for " + licensePlateInfo.getPlateId() + " on camera " + camera.getCameraId() + ".");

        //Save in repositories
        //TODO: save in repositories
    }
}
