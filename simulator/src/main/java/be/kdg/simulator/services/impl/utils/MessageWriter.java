package be.kdg.simulator.services.impl.utils;

import be.kdg.simulator.configs.MessageWriterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * custom writer service
 */
@Service
public class MessageWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageWriter.class);
    private final MessageWriterConfig messageWriterConfig;

    @Autowired
    public MessageWriter(MessageWriterConfig messageWriterConfig) {
        this.messageWriterConfig = messageWriterConfig;
    }

    /**
     * generic static method that that is used to write toString()-method
     * to a file
     *
     * @param message message that needs to be saved
     */
    public void writeMessage(Object message) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(messageWriterConfig.getFilePath(), true)))) {
            writer.write(message.toString() + "\n");
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }
}
