package be.kdg.simulator.services.impl.messengers;

import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.api.Messenger;
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
