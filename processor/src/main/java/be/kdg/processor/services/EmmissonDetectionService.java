package be.kdg.processor.services;

import be.kdg.processor.config.ExternalServiceConfig;
import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.services.parsingServices.JSONService;
import be.kdg.sa.services.CameraServiceProxy;
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

    @Autowired
    public EmmissonDetectionService(CameraServiceProxy cameraServiceProxy, LicensePlateServiceProxy licensePlateServiceProxy) {
        this.cameraServiceProxy = cameraServiceProxy;
        this.licensePlateServiceProxy = licensePlateServiceProxy;
    }

    @Override
    public void detectFine(CameraMessage message) {
        try {
            String cameraJson = licensePlateServiceProxy.get(message.getLicensePlate());
            System.out.println(JSONService.unparse(cameraJson));
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log exception
            //TODO: throw exception
        }
    }
}
