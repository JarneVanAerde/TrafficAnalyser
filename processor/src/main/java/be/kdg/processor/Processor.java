package be.kdg.processor;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.receivers.Receiver;
import be.kdg.processor.services.DetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);
    private final Receiver<CameraMessage> receiver;
    private final List<DetectionService<CameraMessage>> detectionServices;

    @Autowired
    public Processor(Receiver<CameraMessage> receiver, List<DetectionService<CameraMessage>> detectionServices) {
        this.receiver = receiver;
        this.detectionServices = detectionServices;
    }

    @Scheduled(fixedDelayString = "${processor.scheduledtime}")
    public void processMessages() {
        List<CameraMessage> cameraMessages = receiver.getBufferdObjects();
        LOGGER.info("Processing " + cameraMessages.size() + " messages.");
        cameraMessages.forEach(
                message -> detectionServices.forEach(service -> service.detectFine(message))
        );
    }
}
