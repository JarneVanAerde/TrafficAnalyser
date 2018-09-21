package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomMessageGeneratorTests {
    //field injection
    @Autowired
    private MessageGenerator messageGenerator;

    @Test
    public void testMessageGenerator() {
        CameraMessage cameraMessage = messageGenerator.generate();
        assertTrue(cameraMessage.getLicensePlate().equalsIgnoreCase("1-ABC-123"));
    }
}
