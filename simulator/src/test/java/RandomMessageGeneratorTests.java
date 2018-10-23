import be.kdg.simulator.configs.FrequencyConfig;
import be.kdg.simulator.configs.GeneratorConfig;
import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.api.MessageGenerator;
import be.kdg.simulator.services.exceptions.ServiceException;
import be.kdg.simulator.services.impl.generators.RandomMessageGenerator;
import be.kdg.simulator.services.impl.utils.FrequencyDecider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RandomMessageGenerator.class, GeneratorConfig.class, FrequencyDecider.class, FrequencyConfig.class})
public class RandomMessageGeneratorTests {
    @Autowired
    private MessageGenerator messageGenerator;

    @Test
    public void testGenerate() throws ServiceException {
        CameraMessage cameraMessage = messageGenerator.generate();
        assertNotNull(cameraMessage);
    }

    @Test
    public void testRandomLicensePlate() throws ServiceException {
        CameraMessage cameraMessage = messageGenerator.generate();
        assertTrue(cameraMessage.getLicensePlate().matches("^[1-8]-[A-Z]{3}-[0-9]{3}$"));
    }

    @Test
    public void testRandomGenration() throws ServiceException {
        assertNotEquals(messageGenerator.generate(), messageGenerator.generate());
    }
}
