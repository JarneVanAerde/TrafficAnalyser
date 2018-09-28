package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messaging.type", havingValue = "cmd")
public class CommandLineMessenger implements Messenger {
    private final MessageGenerator messageGenerator;

    @Autowired
    public CommandLineMessenger(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    /**
     * Generates a message from the file -or random generator
     * and prints it to the commandline.
     */
    @Override
    public void sendMessage() {
        System.out.println(messageGenerator.generate());
    }
}
