package be.kdg.processor.receivers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${messaging.queue.name}")
public class MessageReceiver {
    @RabbitHandler
    public void receive(String message) {
        System.out.println("test");
        System.out.println(message + " received.");
    }
}

