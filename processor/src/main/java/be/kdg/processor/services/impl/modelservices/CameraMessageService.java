package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.persistence.CameraMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraMessageService {
    private final CameraMessageRepository cameraMessageRepository;

    @Autowired
    public CameraMessageService(CameraMessageRepository cameraMessageRepository) {
        this.cameraMessageRepository = cameraMessageRepository;
    }

    public CameraMessage createCameraMessage(CameraMessage message) {
        return cameraMessageRepository.save(message);
    }
}
