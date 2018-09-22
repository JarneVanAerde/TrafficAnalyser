package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used for making random messages
 */
@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator {
    @Value("${generator.message.maxid}")
    private int maxId;
    private final Random generator;
    private final List<Integer> messageIds;

    /**
     * Used for lazy initialisation of idGenerator and userids
     */
    public RandomMessageGenerator() {
        generator = new Random();
        messageIds = new ArrayList<>();
    }

    /**
     * Apache common plug-in was used for this method.
     * First number needs be generated with normal generator,
     * because belgian licencing plate's first number has a range of 1-8
     * @return a random generated licencing plate
     */
    private String generateLicenseplate() {
        return String.format("%s-%s-%s",
                generator.nextInt(8) + 1,
                RandomStringUtils.random(3, true, false).toUpperCase(),
                RandomStringUtils.random(3, false, true));
    }

    /*
    /**
     * first we generate a random id
     * If that id is already in the list, than we generate another one
     * @return gives back a random generated message-id that is unique.
     *
    private int getUniqueId() {
        try {
            if (messageIds.size() >= maxId) throw new Exception("All unique CameraMessages are generated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int messageId;
        boolean foundId = false;
        do {
            messageId = idGenerator.nextInt(maxId) + 1;
            if (!messageIds.contains(messageId)) {
                foundId = true;
                messageIds.add(messageId);
            }
        } while (!foundId);


        return messageId;
    }*/

    /**
     * @return gives back a newly generated CameraMessage-instance based on random parameters
     */
    @Override
    public CameraMessage generate() {
        return new CameraMessage(generator.nextInt(maxId) + 1, generateLicenseplate(), LocalDateTime.now());
    }
}
