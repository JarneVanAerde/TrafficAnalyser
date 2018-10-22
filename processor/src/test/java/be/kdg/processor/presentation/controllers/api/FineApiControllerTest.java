package be.kdg.processor.presentation.controllers.api;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.modelservices.CameraMessageService;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FineApiControllerTest {
    private static final String PLATE_ID = "1-ABC-123";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FineService fineService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private CameraMessageService cameraMessageService;

    @Before
    public void setUp() throws ServiceException {
        vehicleService.extractPlateInfo(new LicensePlateInfo(PLATE_ID, "NaN", 3));
        fineService.createEmissionFine(3, 3,
                new CameraMessage(3, PLATE_ID, LocalDateTime.now()),
                PLATE_ID);
    }

    @After
    public void tearDown() {
        fineService.deleteAllFines();
        cameraMessageService.deleteAllMessage();
        vehicleService.deleteAllOwnersAndVehicles();
    }

    @Test
    public void testLoadFines() throws Exception {
        mockMvc.perform(get("/api/fines/" + findFineId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testApproveFine() throws Exception {
        mockMvc.perform(put("/api/fines/approve/" + findFineId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        assertTrue(fineService.getFine(findFineId()).isApproved());
    }

    @Test
    public void testUpdateAmount() throws Exception {
        final String jsonString = "{\n" +
                "  \"amount\": 1000.0,\n" +
                "  \"motivation\": \"Changed for now reason :))\"\n" +
                "}";

        mockMvc.perform(put("/api/fines/updateAmount/" + findFineId())
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
                .andExpect(status().isOk());
        assertFalse(fineService.getFine(findFineId()).getChangeAmountMotivation().equalsIgnoreCase("default"));
    }

    private int findFineId()  {
        return fineService.getFines().stream()
                .filter(fine -> fine instanceof EmissionFine)
                .filter(fine -> ((EmissionFine) fine).getEmmisionMessage().getLicensePlate().equalsIgnoreCase(PLATE_ID))
                .findAny().get().getFineId();
    }
}