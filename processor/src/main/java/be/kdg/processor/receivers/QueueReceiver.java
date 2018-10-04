package be.kdg.processor.receivers;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.services.parsingServices.XMLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RabbitListener(queues = "${messaging.queue.name}")
@ConditionalOnProperty(name = "receiver.type", havingValue = "queue")
public class QueueReceiver implements Receiver<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueReceiver.class);
    private final List<CameraMessage> messageBuffer;

    public QueueReceiver() {
        this.messageBuffer = new ArrayList<>();
    }

    @Override
    public List<CameraMessage> getBufferdObjects() {
        List<CameraMessage> buffer = new ArrayList<>(Collections.unmodifiableList(messageBuffer));
        messageBuffer.clear();
        return buffer;
    }

    @Override
    @RabbitHandler
    public void receiveMessage(String message) throws IOException {
        CameraMessage cameraMessage = (CameraMessage) XMLService.unmarshel(message, CameraMessage.class);
        messageBuffer.add(cameraMessage);
        LOGGER.info("Message with license plate " + cameraMessage.getLicensePlate() + " has been received.");
    }
}
