package be.kdg.processor;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.receivers.Receiver;
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

    @Autowired
    public Processor(Receiver<CameraMessage> receiver) {
        this.receiver = receiver;
    }

    @Scheduled(fixedDelayString = "${processor.scheduledtime}")
    public void processMessages() {
        List<CameraMessage> cameraMessages = receiver.getBufferdObjects();
        LOGGER.info("Processing " + cameraMessages.size() + " messages.");
        cameraMessages.forEach(fineDetectionService::detectFine);
    }
}
