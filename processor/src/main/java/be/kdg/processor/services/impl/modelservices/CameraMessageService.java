package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.cameras.Segment;
import be.kdg.processor.persistence.CameraMessageRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
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
    private final CameraInfoService cameraInfoService;

    @Autowired
    public CameraMessageService(CameraMessageRepository cameraMessageRepository, CameraInfoService cameraInfoService) {
        this.cameraMessageRepository = cameraMessageRepository;
        this.cameraInfoService = cameraInfoService;
    }

    public CameraMessage saveCameraMessage(CameraMessage message) {
        return cameraMessageRepository.save(message);
    }

    public Optional<CameraMessage> getConnectedMessage(String plateId, int cameraSegmentId) throws PersistenceException {
        return cameraMessageRepository.findAll()
                .stream()
                .filter(cm -> cm.getLicensePlate().equalsIgnoreCase(plateId) && cm.getId() == cameraSegmentId)
                .sorted(Comparator.comparing(CameraMessage::getTimestamp))
                .findAny();
    }

    public Optional<CameraMessage> getConnectedMessageForEmptySegment(String plateId, int cameraId) {
        List<CameraMessage> cameraMessages = cameraMessageRepository.findAll()
                .stream()
                .filter(cm -> cm.getLicensePlate().equalsIgnoreCase(plateId))
                .collect(Collectors.toList());

        try {
            for (CameraMessage cm : cameraMessages) {
                Segment cameraSegment = cameraInfoService.get(cm.getId()).getSegment();
                if (cameraSegment != null && cameraSegment.getConnectedCameraId() == cameraId) return Optional.of(cm);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
