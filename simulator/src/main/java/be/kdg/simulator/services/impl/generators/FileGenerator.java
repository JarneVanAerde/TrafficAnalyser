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
public class FileGenerator implements MessageGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileGenerator.class);
    private final GeneratorConfig generatorConfig;
    private final List<CameraMessage> cameraMessages;
    private int counter;

    @Autowired
    public FileGenerator(GeneratorConfig generatorConfig) throws ServiceException {
        this.generatorConfig = generatorConfig;
        this.cameraMessages = extractdata();
        this.counter = 0;
    }

    /**
     * File-reader that reads a given file.
     * Information about camera messages is loaded into CameraMessage-objects.
     * Delay is used to calculate the next LocalDateTime value.
     *
     * @return A list of Camera messages
     */
    private List<CameraMessage> extractdata() throws ServiceException {
        List<CameraMessage> cameraMessages = new ArrayList<>();

        try (Scanner scanner = new Scanner(generatorConfig.getFilePath())) {
            final int NANO_SECONDS = 1000000;
            LocalDateTime localDateTimeForMessage = LocalDateTime.now();
            int delay;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                delay = Integer.parseInt(values[2]);
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
            throw new ServiceException(getClass().getSimpleName() + ": " + ioe.getMessage());
        }

        LOGGER.info("Extraction file succeeded.");
        return cameraMessages;
    }

    /**
     * Every time we return a new camera message, a counter will be incremented
     * to make sure the next value is returned when the method is called again.
     * If the counter is at the end of the collection, then the program will be exited.
     *
     * @return A generated camera message that was extracted from the file previously.
     */
    @Override
    public CameraMessage generate() {
        if (counter >= cameraMessages.size()) System.exit(0);
        return cameraMessages.get(counter++);
    }
}
