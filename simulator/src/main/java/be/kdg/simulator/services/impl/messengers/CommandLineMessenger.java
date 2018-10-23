package be.kdg.simulator.services.impl.messengers;

import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.api.Messenger;
import be.kdg.simulator.services.impl.utils.MessageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Messenger used to send messages to the command line.
 */
@Component
@ConditionalOnProperty(name = "messaging.type", havingValue = "cmd")
public class CommandLineMessenger implements Messenger {
    private final MessageWriter messageWriter;

    @Autowired
    public CommandLineMessenger(MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    /**
     * Generates a message from the file -or random generator
     * and prints it to the commandline.
     */
    @Override
    public void sendMessage(CameraMessage message) {
        messageWriter.writeMessage(message);
        System.out.println(message);
    }
}
