package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.cameras.Segment;
import be.kdg.processor.persistence.CameraMessageRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This service is used to buffer camera messages for calculating
 * speed fines. is the message is corresponding with a segment,
 * then a fine will be created
 */
@Service
@Transactional
public class CameraMessageService {
    private final CameraMessageRepository cameraMessageRepository;
    private final CameraInfoService cameraInfoService;
    private final List<CameraMessage> messageBuffer;

    @Autowired
    public CameraMessageService(CameraMessageRepository cameraMessageRepository, CameraInfoService cameraInfoService) {
        this.cameraMessageRepository = cameraMessageRepository;
        this.cameraInfoService = cameraInfoService;
        this.messageBuffer = new ArrayList<>();
    }

    /**
     * @param message message to save
     * @return the saved camera messages.
     */
    public CameraMessage saveMessage(CameraMessage message) {
        return cameraMessageRepository.save(message);
    }

    /**
     * Adds the message to the buffer
     * @param message message to buffer
     */
    public void addToBuffer(CameraMessage message) {
        messageBuffer.add(message);
    }

    /**
     *
     * @param plateId the plate id of the connected message
     * @param cameraId the camera id of the segment
     * @return the connected camera-message if there is one present.
     * @throws ServiceException wrapper-exception
     */
    public Optional<CameraMessage> getConnectedMessageForEmptySegment(String plateId, int cameraId) throws ServiceException {
        List<CameraMessage> cameraMessages = messageBuffer.stream()
                .filter(cm -> cm.getLicensePlate().equalsIgnoreCase(plateId))
                .collect(Collectors.toList());

        for (CameraMessage cm : cameraMessages) {
            Segment cameraSegment = cameraInfoService.get(cm.getCameraId()).getSegment();
            if (cameraSegment != null && cameraSegment.getConnectedCameraId() == cameraId) return Optional.of(cm);
        }

        return Optional.empty();
    }
}
