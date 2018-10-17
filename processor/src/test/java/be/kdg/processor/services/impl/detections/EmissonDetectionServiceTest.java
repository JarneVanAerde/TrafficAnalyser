package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import org.apache.tomcat.jni.Local;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmissonDetectionServiceTest {
    @Autowired
    private EmissonDetectionService emissonDetectionService;
    private FineService f
    private CameraMessage cm1;
    private CameraMessage cm2;


    @Before
    public void setUp() throws Exception {
        cm1 = new CameraMessage(4, "1-ABC-123", LocalDateTime.now());
        cm2 = new CameraMessage(5, "1-ABC-123", LocalDateTime.now());
    }

    @After
    public void tearDown() {
        cm1 = null;
        cm2 = null;
    }

    @Test
    public void detectFineSucces() throws ServiceException {
        emissonDetectionService.detectFine(cm1);
        emissonDetectionService.detectFine(cm2);

    }

    @Test
    public void detectFineFail() throws ServiceException {
        emissonDetectionService.detectFine(cm1);
        emissonDetectionService.detectFine(cm2);
    }
}