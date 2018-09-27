package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;
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
    private static Path filePath = Paths.get("simulator/cameramessages.txt");
    private final List<CameraMessage> cameraMessages;
    private int counter;

    public FileGenerator() {
        this.cameraMessages = extractdata();
        //this.counter
    }

    private List<CameraMessage> extractdata() {
        List<CameraMessage> cameraMessages = new ArrayList<>();

        try (Scanner scanner = new Scanner(filePath)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                cameraMessages.add(new CameraMessage(
                        Integer.parseInt(values[0]),
                        values[1],
                        initializeLocalDateTime(values[2])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log exceptio,
            //TODO: rethrow exception
        }

        return cameraMessages;
    }

    private LocalDateTime initializeLocalDateTime(String localDateTimeString) {
        String[] dateValues = localDateTimeString.split(" ")[0].split("-");
        String[] timeValues = localDateTimeString.split(" ")[1].split(":");

        List<Integer> dvcon = Arrays.stream(dateValues).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> tmcon = Arrays.stream(timeValues).map(Integer::parseInt).collect(Collectors.toList());

        return LocalDateTime.of(dvcon.get(0), dvcon.get(1), dvcon.get(2),
                tmcon.get(0), tmcon.get(1), tmcon.get(2));
    }

    @Override
    public CameraMessage generate() {
        return new CameraMessage(2, "1-GOD-999", LocalDateTime.now());
    }
}
