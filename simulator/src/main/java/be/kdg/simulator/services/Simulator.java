package be.kdg.simulator.services;

import be.kdg.simulator.services.api.MessageGenerator;
import be.kdg.simulator.services.api.Messenger;
import be.kdg.simulator.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public Simulator(MessageGenerator generator, Messenger messenger) {
        this.generator = generator;
        this.messenger = messenger;
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