package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CommandLineMessenger implements Messenger {
    private final MessageGenerator messageGenerator;

    //Consturctor injection
    @Autowired
    public CommandLineMessenger(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    @Override
    public void sendMessage() {
        System.out.println(messageGenerator.generate());
    }
}
