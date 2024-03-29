package be.kdg.processor.services.impl.receivers;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.services.api.ProcessorService;
import be.kdg.processor.services.api.Receiver;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class listens for messages that are on the queue.
 * If a message is on the queue, then it will be passed to the
 * processor. the type of processor is mentioned in application.properties.
 */
@Component
public class QueueReceiver implements Receiver<CameraMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueReceiver.class);
    private final XmlMapper xmlMapper;
    private final ProcessorService<CameraMessage> processorService;

    @Autowired
    public QueueReceiver(XmlMapper xmlMapper, ProcessorService<CameraMessage> processorService) {
        this.xmlMapper = xmlMapper;
        this.processorService = processorService;
    }

    /**
     * This class handles all the messages that come from the queue
     *
     * @param message The message from the queue.
     * @throws IOException Throws an exception if the conversion from xml to object failed.
     */
    @Override
    @RabbitListener(id = "${queue.id}", queues = "${messaging.queue.name}")
    public void receiveMessage(String message) throws IOException {
        CameraMessage cameraMessage = xmlMapper.readValue(message, CameraMessage.class);
        LOGGER.info("Message with license plate " + cameraMessage.getLicensePlate() + " from camera " + cameraMessage.getCameraId() + " has been received.");
        processorService.processMessage(cameraMessage);
    }
}
