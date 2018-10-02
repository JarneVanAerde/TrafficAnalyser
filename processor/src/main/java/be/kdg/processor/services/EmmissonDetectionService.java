package be.kdg.processor.services;

import be.kdg.processor.models.cameras.CameraMessage;
import org.springframework.stereotype.Component;

@Component
public class EmmissonDetectionService implements DetectionService<CameraMessage> {
    @Override
    public void detectFine(CameraMessage message) {

    }
}
