package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator {
    private final List<CameraMessage> cameraMessages;

    public FileGenerator() {
        cameraMessages = extractCameraMessages();
    }

    private List<CameraMessage> extractCameraMessages() {
        return null;
    }

    @Override
    public CameraMessage generate() {
        return new CameraMessage(2, "1-GOD-999", LocalDateTime.now());
    }
}
