package be.kdg.simulator.messengers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommandLineMessengerTest {
    //field injection
    @Autowired
    private Messenger messenger;

    @Test
    public void sendMessage() {
        messenger.sendMessage();
    }
}