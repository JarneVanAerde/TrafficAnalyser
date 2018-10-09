package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.persistence.CameraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraService {
    private final CameraRepository cameraRepository;

    @Autowired
    public CameraService(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }
}
