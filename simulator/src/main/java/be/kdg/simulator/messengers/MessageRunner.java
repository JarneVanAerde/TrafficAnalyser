package be.kdg.simulator.messengers;

import be.kdg.simulator.SimulatorApplication;
import be.kdg.simulator.receivers.Receiver;
import javafx.application.Application;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MessageRunner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public MessageRunner(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(SimulatorApplication.topicExcahgneName, "foo.bar.baz", "hello from rabbitmq!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
