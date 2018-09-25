package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messaging.type", havingValue = "queue")
public class QueueMessenger implements Messenger {
    private final MessageGenerator messageGenerator;
    private final RabbitTemplate rabbitTemplate;
    private final Queue camQueue;

    //Consturctor injection
    @Autowired
    public QueueMessenger(MessageGenerator messageGenerator, Queue camQueue, RabbitTemplate rabbitTemplate) {
        this.messageGenerator = messageGenerator;
        this.rabbitTemplate = rabbitTemplate;
        this.camQueue = camQueue;
    }

    @Override
    public void sendMessage() {
        CameraMessage cameraMessage = messageGenerator.generate();
        rabbitTemplate.convertAndSend(camQueue.getName(), cameraMessage.toString());
        System.out.println("Message " + cameraMessage.getId() + " with license plate " + cameraMessage.getLicensePlate() + " has been sent to the queue.");
    }
}
