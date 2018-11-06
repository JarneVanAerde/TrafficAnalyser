package be.kdg.simulator.services.impl.utils;

import be.kdg.simulator.configs.MessageWriterConfig;
import be.kdg.simulator.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * custom writer service
 */
@Service
public class MessageWriter {
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
    public void writeMessage(Object message) throws ServiceException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(messageWriterConfig.getFilePath(), true)))) {
            writer.write(message.toString() + "\n");
        } catch (IOException e) {
            throw new ServiceException(getClass().getSimpleName() + ": "  + e.getMessage());
        }
    }
}
