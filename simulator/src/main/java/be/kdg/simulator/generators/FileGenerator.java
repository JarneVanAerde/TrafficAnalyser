package be.kdg.simulator.generators;

import be.kdg.simulator.configs.GeneratorConfig;
import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileGenerator.class) ;
    private final GeneratorConfig generatorConfig;
    private final List<CameraMessage> cameraMessages;
    private final List<Integer> delays;
    private int counter;

    @Autowired
    public FileGenerator(GeneratorConfig generatorConfig) throws IOException {
        this.generatorConfig = generatorConfig;
        this.delays = new ArrayList<>();
        this.cameraMessages = extractdata();
        this.counter = 0;
    }

    /**
     * Bases file-reader that reads a given file.
     * Information about camera messages is loaded into CameraMessage-objects.
     * Information about delays is loaded into a separate collection.
     *
     * @return a list of Camera messages
     */
    private List<CameraMessage> extractdata() throws IOException {
        List<CameraMessage> cameraMessages = new ArrayList<>();

        try (Scanner scanner = new Scanner(generatorConfig.getFilePath())) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                if (Integer.parseInt(values[0]) < generatorConfig.getMaxId()) {
                    cameraMessages.add(new CameraMessage(
                            Integer.parseInt(values[0]),
                            values[1],
                            initializeLocalDateTime(values[2])
                    ));

                    delays.add(Integer.parseInt(values[3]));
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("Extraction of file data failed.");
            throw ioe;
        }

        LOGGER.info("Extraction file succeeded.");
        return cameraMessages;
    }

    /**
     * This method uses regex to further extract the information out of the LocalDateTimeString.
     *
     * @param localDateTimeString the string extracted from the file
     * @return a LocalDateTime object
     */
    private LocalDateTime initializeLocalDateTime(String localDateTimeString) {
        String[] dateValues = localDateTimeString.split(" ")[0].split("-");
        String[] timeValues = localDateTimeString.split(" ")[1].split(":");

        List<Integer> dvcon = Arrays.stream(dateValues).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> tmcon = Arrays.stream(timeValues).map(Integer::parseInt).collect(Collectors.toList());

        return LocalDateTime.of(dvcon.get(0), dvcon.get(1), dvcon.get(2),
                tmcon.get(0), tmcon.get(1), tmcon.get(2));
    }

    /**
     * Every time we return a new camera message, a counter will be incremented
     * to make sure the next value is returned when the function is called again.
     *
     * If the counter is at the end of the array, then it will be reset to 0 to
     * make sure we keep returning messages.
     *
     * The time that is scheduled is subtracted from
     *
     * NOTE:
     * The time it takes to generate a message is depended on not only the delay,
     * but also the scheduled time.
     *
     * @return a generated camera message that was extracted from the file previously.
     */
    @Override
    public CameraMessage generate() {
        if (counter >= cameraMessages.size()) System.exit(0);

        int dif = delays.get(counter) - generatorConfig.getSchedulingTime();
        if (dif > 0) {
            try {
                Thread.sleep(dif);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //TODO: log exception
                //TODO: rethrow if needed
            }
        }

        return cameraMessages.get(counter++);
    }
}
