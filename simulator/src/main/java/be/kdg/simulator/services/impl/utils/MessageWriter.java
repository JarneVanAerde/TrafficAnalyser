package be.kdg.simulator.services.impl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class MessageWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageWriter.class);

    public static void writeMessage(Object message) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("simulator/messages.txt", true)))) {
            writer.write(message.toString() + "\n");
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }
}
