package be.kdg.processor;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.services.XMLService;
import be.kdg.sa.services.CameraServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RabbitListener(queues = "${messaging.queue.name}")
public class Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);
    private final List<CameraMessage> cameraMessages;

    public Processor() {
        this.cameraMessages = new ArrayList<>();
    }

    @Scheduled(fixedDelayString = "${processor.scheduledtime}")
    public void processMessage() {
        LOGGER.info("Processing " + cameraMessages.size() + " messages.");

        //TODO: implement delegation to finedection service

        cameraMessages.clear();
    }

    @RabbitHandler
    public void receive(String message) throws IOException {
        System.out.println(message);
        CameraMessage cameraMessage = XMLService.unmarshel(message);
        cameraMessages.add(cameraMessage);
        LOGGER.info("Message with license plate " + cameraMessage.getLicensePlate() + " has been received.");
    }
}
