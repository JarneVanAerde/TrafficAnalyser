package be.kdg.simulator.generators;

import be.kdg.simulator.generators.config.GeneratorConfig;
import be.kdg.simulator.model.CameraMessage;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@NoArgsConstructor
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator {
    @Override
    public CameraMessage generate() {
        return new CameraMessage(2, "1-GOD-999", LocalDateTime.now());
    }
}
