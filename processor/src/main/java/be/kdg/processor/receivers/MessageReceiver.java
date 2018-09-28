package be.kdg.processor.receivers;

import be.kdg.processor.models.CameraMessage;
import be.kdg.processor.services.XMLService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "${messaging.queue.name}")
public class MessageReceiver {
    @RabbitHandler
    public void receive(String message) {
        System.out.println(XMLService.unmarshel(message));
    }
}

