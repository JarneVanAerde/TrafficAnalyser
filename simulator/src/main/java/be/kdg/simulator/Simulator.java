package be.kdg.simulator;

import be.kdg.simulator.messengers.Messenger;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Simulator {
    private final Messenger messenger;

    @Autowired
    public Simulator(Messenger messenger) {
        this.messenger = messenger;
    }

    @Scheduled(fixedDelayString = "${generator.scheduledtime}")
    public void GenerateMessages() throws JsonProcessingException {
        messenger.sendMessage();
    }
}