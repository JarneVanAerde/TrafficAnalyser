package be.kdg.processor.services.impl.detections;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.impl.modelservices.FineService;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EmissonDetectionServiceTest {
    private static final String PLATE_ID = "1-ABC-123";

    @Autowired
    private EmissionFineDetectionService emissonDetectionService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private FineService fineService;

    @Test
    public void testDetectEmissionFineSucces() throws Exception {
        CameraMessage testMessage = new CameraMessage(3, PLATE_ID, LocalDateTime.now());
        vehicleService.extractPlateInfo(new LicensePlateInfo(PLATE_ID, "NaN", 1));

        emissonDetectionService.detectFine(testMessage);
        fineService.getFine(findFineByPlate());
        Assert.assertEquals(300.0, fineService.getFine(findFineByPlate()).getAmount(), 0.0);
    }

    @Test(expected = Exception.class)
    public void testDetectEmissionFineFail() throws Exception {
        CameraMessage message = new CameraMessage(1, PLATE_ID, LocalDateTime.now());
        vehicleService.extractPlateInfo(new LicensePlateInfo(PLATE_ID, "NaN", 1));

        emissonDetectionService.detectFine(message);
        fineService.getFine(findFineByPlate());
        Assert.fail("There should not be a fine in the database...");
    }

    /**
     * Used for retrieving the find id.
     * @return corresponding fine Id.
     */
    private int findFineByPlate()  {
        return fineService.getFines().stream()
                .filter(fine -> fine instanceof EmissionFine)
                .filter(fine -> ((EmissionFine) fine).getEmmisionMessage().getLicensePlate().equalsIgnoreCase(PLATE_ID))
                .findAny().get().getFineId();
    }
}