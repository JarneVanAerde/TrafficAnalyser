package be.kdg.simulator.services.impl.generators;

import be.kdg.simulator.configs.GeneratorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is used fot hte execution of different file readers.
 * It enables us to parallel read files.
 */
@Component
public class FileGeneratorExecutor {
    private final FileGenerator fileGenerator;
    private final GeneratorConfig generatorConfig;
    private final ExecutorService executor;

    @Autowired
    public FileGeneratorExecutor(FileGenerator fileGenerator, GeneratorConfig generatorConfig) {
        this.fileGenerator = fileGenerator;
        this.generatorConfig = generatorConfig;
        this.executor = Executors.newFixedThreadPool(generatorConfig.getNumberOfFiles());
        executeFileReaders();
    }

    /**
     * This method executed the file readers.
     */
    private void executeFileReaders() {
        for (int i = 0; i < generatorConfig.getNumberOfFiles(); i++) {
            executor.execute(fileGenerator);
        }
    }
}
