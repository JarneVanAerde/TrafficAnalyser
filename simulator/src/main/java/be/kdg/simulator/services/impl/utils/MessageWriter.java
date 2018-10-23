package be.kdg.simulator.services.impl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * custom writer service
 */
@Service
public class MessageWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageWriter.class);

    /**
     * generic static method that that is used to write toString()-method
     * to a file
     *
     * @param message message that needs to be saved
     */
    public static void writeMessage(Object message) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("simulator/messages.txt", true)))) {
            writer.write(message.toString() + "\n");
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }
}
