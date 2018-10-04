package be.kdg.processor.externalServices;

import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CameraServiceTest {
    @Autowired
    private CameraServiceProxy cameraServiceProxy;

    @Test
    public void testGet() throws IOException {
        String cameraJson = cameraServiceProxy.get(1);
        System.out.println(cameraJson);
    }

    @Test(expected = IOException.class)
    public void testCommunicationError() throws IOException {
        cameraServiceProxy.get(101);
        Assert.fail("Camera id needs to force a communication error");
    }

    @Test(expected = CameraNotFoundException.class)
    public void testCameraNotFound() throws CameraNotFoundException, IOException {
        cameraServiceProxy.get(235);
        Assert.fail("Camera id needs to throw an CameraNotFoundException");
    }
}
