package be.kdg.simulator.generators;

import be.kdg.simulator.configs.GeneratorConfig;
import be.kdg.simulator.model.CameraMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator {
    private final GeneratorConfig generatorConfig;
    private final List<CameraMessage> cameraMessages;
    private int counter;

    @Autowired
    public FileGenerator(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        this.cameraMessages = extractdata();
        this.counter = 0;
    }

    /**
     * Bases file-reader that reads a given file.
     *
     * @return a list of Camera messages
     */
    private List<CameraMessage> extractdata() {
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log exceptio,
            //TODO: rethrow exception
        }

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
     * @return a generated camera message that was extracted from the file previously.
     */
    @Override
    public CameraMessage generate() {
        if (counter >= cameraMessages.size()) counter = 0;
        return cameraMessages.get(counter++);
    }
}
