package be.kdg.processor;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.services.XMLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "${messaging.queue.name}")
public class Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    @RabbitHandler
    public void receive(String message) throws IOException {
        CameraMessage cameraMessage = XMLService.unmarshel(message);
        LOGGER.info("Message with license plate " + cameraMessage.getLicensePlate() + " has been received.");
    }
}
