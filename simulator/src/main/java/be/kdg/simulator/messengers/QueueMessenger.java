package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.FileGenerator;
import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import be.kdg.simulator.services.XMLService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessenger.class) ;

    //Consturctor injection
    @Autowired
    public QueueMessenger(MessageGenerator messageGenerator, Queue camQueue, RabbitTemplate rabbitTemplate) {
        this.messageGenerator = messageGenerator;
        this.rabbitTemplate = rabbitTemplate;
        this.camQueue = camQueue;
    }

    /**
     * Generates a message from the file -or random generator
     * Generates a xml-formatted message based on the XMLSerice class
     * After that, the message is send using a remote proxy.
     */
    @Override
    public void sendMessage() throws JsonProcessingException {
        CameraMessage cameraMessage = messageGenerator.generate();
        rabbitTemplate.convertAndSend(camQueue.getName(), XMLService.marshel(cameraMessage));
        LOGGER.info("Message with license " + cameraMessage.getLicensePlate() + " has been sent to the queue");
    }
}
