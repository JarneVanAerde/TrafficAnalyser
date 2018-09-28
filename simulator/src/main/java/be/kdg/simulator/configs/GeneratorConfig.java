package be.kdg.simulator.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class GeneratorConfig {
    @Getter
    @Value("${generator.message.maxid}")
    private int maxId;
    @Getter
    @Value("${generator.scheduledtime}")
    private int schedulingTime;
    @Value("${generator.filepath}")
    private String filePath;

    public Path getFilePath() {
        return Paths.get(filePath);
    }
}
