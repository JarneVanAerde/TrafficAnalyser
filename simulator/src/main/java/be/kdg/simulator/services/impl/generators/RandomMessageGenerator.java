package be.kdg.simulator.services.impl.generators;

import be.kdg.simulator.configs.GeneratorConfig;
import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.api.MessageGenerator;
import be.kdg.simulator.services.exceptions.ServiceException;
import be.kdg.simulator.services.impl.utils.FrequencyDecider;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used for making random messages.
 */
@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator {
    private final Random random;
    private final GeneratorConfig generatorConfig;
    private final FrequencyDecider frequencyDecider;
    private final List<CameraMessage> cameraMessagesPreset;

    @Autowired
    public RandomMessageGenerator(GeneratorConfig generatorConfig, FrequencyDecider frequencyDecider) {
        this.generatorConfig = generatorConfig;
        this.frequencyDecider = frequencyDecider;
        this.random = new Random();
        this.cameraMessagesPreset = new ArrayList<>();
        generateRandomPlates();
    }

    /**
     * Generates fixed random plates for more
     * realistic behavior.
     */
    private void generateRandomPlates() {
        for (int i = 0; i < generatorConfig.getNumberOfFixedMessages(); i++)
            cameraMessagesPreset.add(new CameraMessage(random.nextInt(generatorConfig.getMaxId()) + 1, generateLicensePlate(), LocalDateTime.now()));
    }

    /**
     * Apache common plug-in was used for this method.
     * First number needs be generated with normal random,
     * because belgian licencing plate's first number has a range of 1-8
     *
     * @return A random generated licencing plate
     */
    private String generateLicensePlate() {
        return String.format("%s-%s-%s",
                random.nextInt(8) + 1,
                RandomStringUtils.random(3, true, false).toUpperCase(),
                RandomStringUtils.random(3, false, true));
    }

    /**
     * This method will check if the wait time between 2 messages
     * was officially long enough.
     *
     * @return a random message form the fixed preset
     */
    private CameraMessage getRandomMessage() {
        boolean notFoundCandidate = true;
        CameraMessage candidate = null;

        do {
            CameraMessage possibleCandidate = cameraMessagesPreset.get(random.nextInt(cameraMessagesPreset.size()));

            long sec = Duration.between(possibleCandidate.getTimestamp(), LocalDateTime.now()).getSeconds();
            if (Duration.between(possibleCandidate.getTimestamp(), LocalDateTime.now()).getSeconds() > 10) {
                notFoundCandidate = false;
                candidate = possibleCandidate;
                candidate.setTimestamp(LocalDateTime.now());
                candidate.setCameraId(random.nextInt(generatorConfig.getMaxId()) + 1);

                cameraMessagesPreset.remove(possibleCandidate);
                cameraMessagesPreset.add(candidate);
            }
        } while (notFoundCandidate);

        return candidate;
    }

    /**
     * Thread.sleep is used to simulate traffic peak hours.
     *
     * @return Gives back a newly generated CameraMessage-instance based on random parameters.
     */
    @Override
    public CameraMessage generate() throws ServiceException {
        try {
            Thread.sleep(frequencyDecider.getFrequency());
        } catch (InterruptedException e) {
            throw new ServiceException(getClass().getSimpleName() + ": " + e.getMessage());
        }

        return getRandomMessage();
    }
}
