package be.kdg.simulator.services.impl.messengers;

import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.api.Messenger;
import be.kdg.simulator.services.exceptions.ServiceException;
import be.kdg.simulator.services.impl.utils.MessageWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Messenger used for places camera messages on an online
 * rabbitMQ cloud instance.
 */
@Component
@ConditionalOnProperty(name = "messaging.type", havingValue = "queue")
public class QueueMessenger implements Messenger {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueMessenger.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue camQueue;
    private final XmlMapper xmlMapper;
    private final MessageWriter messageWriter;

    @Autowired
    public QueueMessenger(RabbitTemplate rabbitTemplate, Queue camQueue, XmlMapper xmlMapper, MessageWriter messageWriter) {
        this.rabbitTemplate = rabbitTemplate;
        this.camQueue = camQueue;
        this.xmlMapper = xmlMapper;
        this.messageWriter = messageWriter;
    }

    /**
     * Generates a message from the file -or random generator
     * Generates a xml-formatted message based on the XmlMapper class
     * After that, the message is send using the rabbit template.
     */
    @Override
    public void sendMessage(CameraMessage message) throws ServiceException {
        try {
            messageWriter.writeMessage(message);
            rabbitTemplate.convertAndSend(camQueue.getName(), xmlMapper.writeValueAsString(message));
            LOGGER.info("Message with license " + message.getLicensePlate() + " from camera " + message.getCameraId() + " has been sent to the queue");
        } catch (JsonProcessingException e) {
            throw new ServiceException(getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
