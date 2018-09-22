package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Stream;

/**
 * This class is used for making random messages
 */
@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator {
    @Value("${generator.message.maxid}")
    private int maxId;
    private final Random idGenerator;

    /**
     * Used for lazy initialisation of idGenerator
     */
    public RandomMessageGenerator() {
        idGenerator = new Random();
    }

    /**
     * Apache common was used for this method
     * @return a random generated licencing plate
     */
    private String generateLicenseplate() {
        return String.format("%s-%s-%s",
                RandomStringUtils.random(1, false, true),
                RandomStringUtils.random(3, true, false).toUpperCase(),
                RandomStringUtils.random(3, false, true));
    }

    /**
     * @return Gives back a newly generated CameraMessage-instance based on random parameters
     */
    @Override
    public CameraMessage generate() {
        return new CameraMessage(idGenerator.nextInt(maxId) + 1, generateLicenseplate(), LocalDateTime.now());
    }
}
