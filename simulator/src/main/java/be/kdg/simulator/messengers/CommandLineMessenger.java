package be.kdg.simulator.messengers;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messaging.type", havingValue = "cmd")
public class CommandLineMessenger implements Messenger {
    /**
     * Generates a message from the file -or random generator
     * and prints it to the commandline.
     */
    @Override
    public void sendMessage(CameraMessage message) {
        System.out.println(message);
    }
}
