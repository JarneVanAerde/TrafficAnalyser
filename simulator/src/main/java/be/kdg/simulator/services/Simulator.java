package be.kdg.simulator.services;

import be.kdg.simulator.services.api.MessageGenerator;
import be.kdg.simulator.services.api.Messenger;
import be.kdg.simulator.services.exceptions.ServiceException;
import be.kdg.simulator.services.impl.utils.FrequencyDecider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The purpose of this class is to send messages via a configured medium.
 */
@Component
public class Simulator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Simulator.class);
    private final MessageGenerator generator;
    private final Messenger messenger;
    private final FrequencyDecider frequencyDecider;

    @Autowired
    public Simulator(MessageGenerator generator, Messenger messenger, FrequencyDecider frequencyDecider) {
        this.generator = generator;
        this.messenger = messenger;
        this.frequencyDecider = frequencyDecider;
    }

    /**
     * This scheduled method will be called on continuously.
     * Until the program stops.
     */
    @PostConstruct
    public void generateMessage() {
        try {
            messenger.sendMessage(generator.generate());
            generateMessage();
        } catch (ServiceException e) {
            LOGGER.error(getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}