package be.kdg.processor.services.impl;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.utils.api.Receiver;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.InvalidLicensePlateException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * This class processes the buffered messages that come from receiver
 * The type of receiver is mentioned in application.properties.
 */
@Component
public class ProcessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorService.class);
    private final Receiver<CameraMessage> receiver;
    private final List<DetectionService<CameraMessage>> detectionServices;

    @Autowired
    public ProcessorService(Receiver<CameraMessage> receiver, List<DetectionService<CameraMessage>> detectionServices) {
        this.receiver = receiver;
        this.detectionServices = detectionServices;
    }

    /**
     * Every x-number of seconds, this method will take all buffered messages
     * and pass them through all the detection services to detect fines.
     */
    @Scheduled(fixedDelayString = "${processor.scheduledtime}")
    public void processMessages() {
        List<CameraMessage> cameraMessages = receiver.getBufferdObjects();
        LOGGER.info("Processing " + cameraMessages.size() + " messages.");

        cameraMessages.forEach(message ->
                detectionServices.forEach(service -> {
                    try {
                        service.detectFine(message);
                    } catch (ServiceException e) {
                        LOGGER.error(e.getMessage());
                    }
                })
        );
    }
}
