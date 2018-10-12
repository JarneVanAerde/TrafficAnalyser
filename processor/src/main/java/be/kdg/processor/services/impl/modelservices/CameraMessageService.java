package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.persistence.CameraMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CameraMessageService {
    private final CameraMessageRepository cameraMessageRepository;

    @Autowired
    public CameraMessageService(CameraMessageRepository cameraMessageRepository) {
        this.cameraMessageRepository = cameraMessageRepository;
    }

    public CameraMessage saveCameraMessage(CameraMessage message) {
        return cameraMessageRepository.save(message);
    }

    public Optional<CameraMessage> getConnectedMessage(String plateId, int segmentCameraId) throws PersistenceException {
        return cameraMessageRepository.findAllMessagesByLicensePlate(plateId)
                .stream()
                .filter(msg -> msg.getId() == segmentCameraId)
                .min(Comparator.comparing(CameraMessage::getTimestamp));
    }
}
