package be.kdg.simulator;

import be.kdg.simulator.messengers.Messenger;
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

    @Scheduled(fixedDelayString = "${simulator.scheduledtime}")
    public void GenerateMessages() {
        messenger.sendMessage();
    }
}
