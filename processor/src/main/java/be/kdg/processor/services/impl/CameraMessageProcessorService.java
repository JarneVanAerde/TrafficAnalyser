package be.kdg.processor.services.impl;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.services.api.ProcessorService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.api.DetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class processes the messages of the receiver
 * The type of receiver is mentioned in application.properties.
 */
@Component
@ConditionalOnProperty(name = "processor.type", havingValue = "cm")
public class CameraMessageProcessorService implements ProcessorService<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CameraMessageProcessorService.class);
    private final List<DetectionService<CameraMessage>> detectionServices;

    @Autowired
    public CameraMessageProcessorService(List<DetectionService<CameraMessage>> detectionServices) {
        this.detectionServices = detectionServices;
    }

    /**
     * processes messages through all the detection services
     * @param message the message that needs to be processed
     */
    @Override
    public void processMessage(CameraMessage message) {
        detectionServices.forEach(service -> {
            try {
                service.detectFine(message);
            } catch (ServiceException e) {
                LOGGER.error(e.getMessage());
            }
        });
    }
}
