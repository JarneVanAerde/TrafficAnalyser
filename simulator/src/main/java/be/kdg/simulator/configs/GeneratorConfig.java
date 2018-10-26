package be.kdg.simulator.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration class used for injecting static values
 * from application.properties that are used by the generator implementations
 */
@Configuration
public class GeneratorConfig {
    @Getter
    @Value("${generator.message.maxid}")
    private int maxId;
    @Value("${generator.filepath}")
    private String filePath;

    public Path getFilePath() {
        return Paths.get(filePath);
    }
}
