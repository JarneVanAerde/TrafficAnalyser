package be.kdg.processor.services;

import be.kdg.processor.models.cameras.CameraMessage;
import org.springframework.stereotype.Service;

@Service
public class SpeedfineDetectionService implements DetectionService<CameraMessage> {
    @Override
    public void detectFine(CameraMessage message) {
        //TODO: implement detection logic.
    }
}
