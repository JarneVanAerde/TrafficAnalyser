package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.exceptions.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleServiceTest {
    @Autowired
    private VehicleService vehicleService;

    @Test
    public void testExtractPlateInfo() throws ServiceException {
        LicensePlateInfo licensePlateInfo1 = new LicensePlateInfo("4-ABC-123", "NaNa", 1);
        vehicleService.extractPlateInfo(licensePlateInfo1);
        LicensePlateInfo licensePlateInfo2 = new LicensePlateInfo("4-ABC-123", "NaNa", 1);
        vehicleService.extractPlateInfo(licensePlateInfo2);

        assertTrue(vehicleService.getVehicle("4-ABC-123").getLicensePlate().equalsIgnoreCase("4-ABC-123"));
        assertEquals(1, vehicleService.getOwner("NaNa").getVehicles().size());
    }
}