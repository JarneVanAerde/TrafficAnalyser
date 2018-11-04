package be.kdg.simulator.services.impl.generators;

import be.kdg.simulator.configs.GeneratorConfig;
import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.api.MessageGenerator;
import be.kdg.simulator.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class main purpose is to generate CameraMessage-objects that come
 * from one or more files.
 */
@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileGenerator.class);
    private final GeneratorConfig generatorConfig;
    private final List<CameraMessage> cameraMessages;
    private int messageCounter;
    private int fileCounter;

    @Autowired
    public FileGenerator(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        this.cameraMessages = new ArrayList<>();
        this.messageCounter = 0;
        this.fileCounter = 0;
    }


    /**
     * File-reader that reads a given file.
     * Information about camera messages is loaded into CameraMessage-objects.
     * Delay is used to calculate the next LocalDateTime value.
     *
     * This method will be called on by the executor service.
     */
    @Override
    public void run() {
        List<CameraMessage> cameraMessages = new ArrayList<>();

        try (Scanner scanner = new Scanner(generatorConfig.getFilePath(fileCounter++))) {
            final int NANO_SECONDS = 1000000;
            LocalDateTime localDateTimeForMessage = LocalDateTime.now();
            long delay;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                delay = Long.parseLong(values[2]);
                localDateTimeForMessage = localDateTimeForMessage.plusNanos(NANO_SECONDS * delay);

                //create new object form line
                if (Integer.parseInt(values[0]) <= generatorConfig.getMaxId()) {
                    cameraMessages.add(new CameraMessage(
                            Integer.parseInt(values[0]),
                            values[1],
                            localDateTimeForMessage
                    ));
                }
            }
        } catch (IOException ioe) {
          LOGGER.warn(getClass().getSimpleName() + ": " + ioe.getMessage());
        }

        LOGGER.info("Extraction file succeeded.");
        this.cameraMessages.addAll(cameraMessages);
    }

    /**
     * Every time we return a new camera message, a messageCounter will be incremented
     * to make sure the next value is returned when the method is called again.
     * If the messageCounter is at the end of the collection, then the program will be exited.
     *
     * @return A generated camera message that was extracted from the file previously.
     */
    @Override
    public CameraMessage generate() {
        if (messageCounter >= cameraMessages.size()) System.exit(0);
        return cameraMessages.get(messageCounter++);
    }


}
