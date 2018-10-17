package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.services.api.DetectionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmissonDetectionServiceTest {
    @Autowired
    private EmissonDetectionService emissonDetectionService;
    private CameraMessage cm1;
    private CameraMessage cm2;


    @Before
    public void setUp() throws Exception {
        cm1 = new CameraMessage();
    }

    @Test
    public void detectFine() {
    }
}