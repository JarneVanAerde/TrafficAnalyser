package be.kdg.processor.receivers;

import be.kdg.processor.models.cameras.CameraMessage;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class listens for messages that are on the queue.
 * If a message is on the queue, then the rabbit handler passes that message to
 * all rabbit handles methods.
 */
@Component
@RabbitListener(queues = "${messaging.queue.name}")
@ConditionalOnProperty(name = "receiver.type", havingValue = "queue")
public class QueueReceiver implements Receiver<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueReceiver.class);
    private final XmlMapper xmlMapper;
    private final List<CameraMessage> messageBuffer;

    @Autowired
    public QueueReceiver(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
        this.messageBuffer = new ArrayList<>();
    }

    /**
     * This method will periodically be called by the processor
     * class to process any buffered message.
     *
     * @return A list of buffered camera messages.
     */
    @Override
    public List<CameraMessage> getBufferdObjects() {
        List<CameraMessage> buffer = new ArrayList<>(Collections.unmodifiableList(messageBuffer));
        messageBuffer.clear();
        return buffer;
    }

    /**
     * This class handles all the messages that come from the queue
     * and adds them to a message buffer.
     *
     * @param message The message from the queue.
     * @throws IOException Throws an exception if the conversion from xml to object failed.
     */
    @Override
    @RabbitHandler
    public void receiveMessage(String message) throws IOException {
        CameraMessage cameraMessage = xmlMapper.readValue(message, CameraMessage.class);
        messageBuffer.add(cameraMessage);
        LOGGER.info("Message with license plate " + cameraMessage.getLicensePlate() + " has been received.");
    }
}
