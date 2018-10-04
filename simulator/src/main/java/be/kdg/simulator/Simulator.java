package be.kdg.simulator;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.messengers.Messenger;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Simulator {
    private final MessageGenerator generator;
    private final Messenger messenger;

    @Autowired
    public Simulator(MessageGenerator generator, Messenger messenger) {
        this.generator = generator;
        this.messenger = messenger;
    }

    @Scheduled(fixedDelayString = "${generator.scheduledtime}")
    public void GenerateMessages() throws JsonProcessingException {
        messenger.sendMessage(generator.generate());
    }
}