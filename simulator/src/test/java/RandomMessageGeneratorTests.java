import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.api.MessageGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void testGenerate() {
        CameraMessage cameraMessage = messageGenerator.generate();
        assertNotNull(cameraMessage);
    }

    @Test
    public void testRandomLicensePlate() {
        CameraMessage cameraMessage = messageGenerator.generate();
        assertTrue(cameraMessage.getLicensePlate().matches("^[1-8]-[A-Z]{3}-[0-9]{3}$"));
    }

    @Test
    public void testRandomGenration() {
        assertNotEquals(messageGenerator.generate(), messageGenerator.generate());
    }
}
