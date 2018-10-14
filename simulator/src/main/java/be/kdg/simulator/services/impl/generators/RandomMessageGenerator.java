package be.kdg.simulator.services.impl.generators;

import be.kdg.simulator.configs.GeneratorConfig;
import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.api.MessageGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * This class is used for making random messages.
 */
@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator {
    private final Random generator;
    private final GeneratorConfig generatorConfig;

    @Autowired
    public RandomMessageGenerator(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        this.generator = new Random();
    }

    /**
     * Apache common plug-in was used for this method.
     * First number needs be generated with normal generator,
     * because belgian licencing plate's first number has a range of 1-8
     *
     * @return A random generated licencing plate
     */
    private String generateLicenseplate() {
        return String.format("%s-%s-%s",
                generator.nextInt(8) + 1,
                RandomStringUtils.random(3, true, false).toUpperCase(),
                RandomStringUtils.random(3, false, true));
    }

    /**
     * @return Gives back a newly generated CameraMessage-instance based on random parameters.
     */
    @Override
    public CameraMessage generate() {
        return new CameraMessage(generator.nextInt(generatorConfig.getMaxId()) + 1, generateLicenseplate(), LocalDateTime.now());
    }
}