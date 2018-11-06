package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.cameras.Segment;
import be.kdg.processor.models.options.OptionKey;
import be.kdg.processor.persistence.CameraMessageRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.adapters.CameraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This service is used to buffer camera messages for calculating
 * speed fines. is the message is corresponding with a segment,
 * then a fine will be created
 */
@Service
@Transactional
public class CameraMessageService {
    private final CameraMessageRepository cameraMessageRepo;
    private final CameraInfoService cameraInfoService;
    private final OptionService optionService;
    private final List<CameraMessage> speedMessageBuffer;

    @Autowired
    public CameraMessageService(CameraMessageRepository cameraMessageRepository, CameraInfoService cameraInfoService, OptionService optionService) {
        this.cameraMessageRepo = cameraMessageRepository;
        this.cameraInfoService = cameraInfoService;
        this.optionService = optionService;
        this.speedMessageBuffer = new ArrayList<>();
    }

    /**
     * @param message message to save
     * @return the saved camera messages.
     */
    public CameraMessage saveMessage(CameraMessage message) {
        return cameraMessageRepo.save(message);
    }

    /**
     * Adds the message to the buffer
     *
     * @param message message to buffer
     */
    public void addToBuffer(CameraMessage message) {
        speedMessageBuffer.add(message);
    }

    /**
     * Removes a message from the buffer
     *
     * @param message message to remove from buffer
     */
    public void removeFromBuffer(CameraMessage message) {
        speedMessageBuffer.remove(message);
    }

    /**
     * @param plateId  the plate id of the connected message
     * @param cameraId the camera id of the segment
     * @return the connected camera-message if there is one present.
     * @throws ServiceException wrapper-exception
     */
    public Optional<CameraMessage> getCorrespondingMessage(String plateId, int cameraId) throws ServiceException {
        deleteMessageOutOfTimeFrame();

        List<CameraMessage> cameraMessages = speedMessageBuffer.stream()
                .filter(cm -> cm.getLicensePlate().equalsIgnoreCase(plateId))
                .sorted(Comparator.comparing(CameraMessage::getTimestamp).reversed())
                .collect(Collectors.toList());

        for (CameraMessage cm : cameraMessages) {
            Segment cameraSegment = cameraInfoService.get(cm.getCameraId()).getSegment();
            if (cameraSegment != null && cameraSegment.getConnectedCameraId() == cameraId) return Optional.of(cm);
        }

        return Optional.empty();
    }

    /**
     * Deletes all the speed messages that exceed the time frame.
     *
     * @throws ServiceException wrapper-exception
     */
    public void deleteMessageOutOfTimeFrame() throws ServiceException {
        double timeFrame = optionService.getOptionValue(OptionKey.TIME_FRAME_SPEED_MESSAGE);
        speedMessageBuffer.removeIf(message ->
                ChronoUnit.MINUTES.between(message.getTimestamp(), LocalDateTime.now()) > timeFrame);
    }
}
