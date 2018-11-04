package be.kdg.simulator.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class used for injecting static values
 * from application.properties that are used by the generator implementations
 */
@Configuration
public class GeneratorConfig {
    @Getter
    @Value("${generator.message.maxid}")
    private int maxId;
    @Value("${generator.numberOfFixedMessages}")
    @Getter
    private int numberOfFixedMessages;
    @Value("${generator.filepath}")
    private String filePath;
    @Value("${generator.numberOfFiles}")
    private int numberOfFiles;

    private final List<Path> paths;

    public GeneratorConfig() {
        this.paths = new ArrayList<>();
    }

    /**4
     * Adds all files to the paths collection after the filepath
     * is injected with the @Value annotation.
     */
    @PostConstruct
    private void addFiles() {
        for (int i = 1; i <= numberOfFiles; i++) {
            paths.add(Paths.get(filePath + "example" + i + ".csv"));
        }
    }

    /**
     * This method is used for calling the right file each time the file generator is executed
     *
     * @param id id of the filepath
     * @return the corresponding message
     */
    public Path getFilePath(int id) {
        return paths.get(id);
    }

    /**
     * Used for determining the size of the thread pool.
     *
     * @return the size of the paths collection.
     */
    public int getNumberOfFiles() {
        return paths.size();
    }
}
