package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.SpeedFine;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.impl.modelservices.FineService;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SpeedDetectionServiceTest {
    private static final String PLATE_ID = "1-ABC-123";

    @Autowired
    private SpeedFineDetectionService speedFineDetectionService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private FineService fineService;

    @Test
    public void testDetectSpeedFineSucces() throws Exception {
        CameraMessage testMessage1 = new CameraMessage(4, PLATE_ID, LocalDateTime.now());
        CameraMessage testMessage2 = new CameraMessage(5, PLATE_ID, LocalDateTime.now().plusSeconds(10));
        vehicleService.extractPlateInfo(new LicensePlateInfo(PLATE_ID, "NaN", 1));

        speedFineDetectionService.detectFine(testMessage1);
        speedFineDetectionService.detectFine(testMessage2);
        fineService.getFine(findFineByPlate());
        Assert.assertEquals(296.0, fineService.getFine(findFineByPlate()).getAmount(), 0.0);
    }

    @Test(expected = Exception.class)
    public void testDetectSpeedFineFail() throws Exception {
        CameraMessage testMessage1 = new CameraMessage(4, PLATE_ID, LocalDateTime.now());
        CameraMessage testMessage2 = new CameraMessage(5, PLATE_ID, LocalDateTime.now().plusMinutes(5));
        vehicleService.extractPlateInfo(new LicensePlateInfo(PLATE_ID, "NaN", 1));

        speedFineDetectionService.detectFine(testMessage1);
        speedFineDetectionService.detectFine(testMessage2);
        fineService.getFine(findFineByPlate());
        Assert.fail("There should not be a fine in the database...");
    }

    private int findFineByPlate() {
        return fineService.getFines().stream()
                .filter(fine -> fine instanceof SpeedFine)
                .filter(fine -> ((SpeedFine) fine).getEnterMessage().getLicensePlate().equalsIgnoreCase(PLATE_ID)
                        && ((SpeedFine) fine).getExitMessage().getLicensePlate().equalsIgnoreCase(PLATE_ID))
                .findAny().get().getFineId();
    }
}
