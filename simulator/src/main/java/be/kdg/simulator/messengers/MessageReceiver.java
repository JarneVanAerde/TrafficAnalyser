package be.kdg.simulator.messengers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RabbitListener(queues = "Cameramessages")
public class MessageReceiver {
    @RabbitHandler
    public void receive(String message) {
        System.out.println(message + " received.");
    }
}
