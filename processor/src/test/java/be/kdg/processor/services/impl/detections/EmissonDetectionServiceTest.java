package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.modelservices.VehicleService;
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
    private EmissionFineDetectionService emissonDetectionService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private FineService fineService;

    @Test
    public void testDetectEmissionFineSucces() throws Exception {
        CameraMessage testMessage = new CameraMessage(3, "1-ABC-123", LocalDateTime.now());
        vehicleService.extractPlateInfo(new LicensePlateInfo("1-ABC-123", "NaN", 1));

        emissonDetectionService.detectFine(testMessage);
        fineService.getFine(findFineByPlate("1-ABC-123"));
        Assert.assertEquals(300.0, fineService.getFine(findFineByPlate("1-ABC-123")).getAmount(), 0.0);
    }

    @Test(expected = Exception.class)
    public void testDetectEmissionFineFail() throws Exception {
        CameraMessage message = new CameraMessage(1, "2-ABC-123", LocalDateTime.now());
        vehicleService.extractPlateInfo(new LicensePlateInfo("2-ABC-123", "NaN", 1));

        emissonDetectionService.detectFine(message);
        fineService.getFine(findFineByPlate("2-ABC-123"));
        Assert.fail("There should not be a fine in the database...");
    }

    private int findFineByPlate(String plate)  {
        return fineService.getFines().stream()
                .filter(fine -> fine instanceof EmissionFine)
                .filter(fine -> ((EmissionFine) fine).getEmmisionMessage().getLicensePlate().equalsIgnoreCase(plate))
                .findAny().get().getFineId();
    }
}