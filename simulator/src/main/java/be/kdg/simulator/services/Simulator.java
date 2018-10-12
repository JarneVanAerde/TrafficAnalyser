package be.kdg.simulator.services;

import be.kdg.simulator.services.api.MessageGenerator;
import be.kdg.simulator.services.api.Messenger;
import be.kdg.simulator.services.exceptions.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The purpose of this class is to send messages via a configured medium.
 */
@Component
public class Simulator {
    private final Logger LOGGER = LoggerFactory.getLogger(Simulator.class);
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
    @Scheduled(fixedDelayString = "${generator.scheduledtime}")
    public void GenerateMessages() {
        try {
            messenger.sendMessage(generator.generate());
        } catch (ServiceException e) {
            LOGGER.error("Something went wrong while parsing an object to another data format " + e.getMessage());
        }
    }
}