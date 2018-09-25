package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.messengers.config.MessengerConfig;
import javafx.application.Application;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(name = "messaging.type", havingValue = "queue")
public class QueueMessenger implements Messenger, CommandLineRunner {
    private final MessageGenerator messageGenerator;
    private final RabbitTemplate rabbitTemplate;
    private final MessageReceiver receiver;

    //Consturctor injection
    @Autowired
    public QueueMessenger(MessageGenerator messageGenerator, MessageReceiver messageReceiver, RabbitTemplate rabbitTemplate) {
        this.messageGenerator = messageGenerator;
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = messageReceiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(MessengerConfig.EXCHANGE_NAME, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void sendMessage() {
        System.out.println("Message " + messageGenerator.generate().getId() + " has been placed on the queue.");
    }
}
