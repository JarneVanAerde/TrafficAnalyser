package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.models.vehicles.Vehicle;
import be.kdg.processor.services.api.DetectionService;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.apache.tomcat.jni.Local;
import org.junit.*;
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
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private FineService fineService;
    private CameraMessage cm1;

    @Before
    public void setUp() throws Exception {
        cm1 = new CameraMessage(3, "1-ABC-123", LocalDateTime.now());
        vehicleService.extractPlateInfo(new LicensePlateInfo("1-ABC-123", "NaN", 1));
    }

    @After
    public void tearDown() {
        cm1 = null;
    }

    @Test
    public void testDetectEmissionFineSucces() throws Exception {
        emissonDetectionService.detectFine(cm1);
        Assert.assertEquals(fineService.getFine(2).getFineId(), 2);
        Assert.assertEquals(300.0, fineService.getFine(2).getAmount(), 0.0);
        fineService.deleteFine(2);
    }

    @Test(expected = ServiceException.class)
    public void testDetectEmissionFineFail() throws Exception {
        cm1 = new CameraMessage(1, "1-ABC-123", LocalDateTime.now());
        emissonDetectionService.detectFine(cm1);
        fineService.getFine(2);
        Assert.fail("There should not be a fine in the database...");
    }
}