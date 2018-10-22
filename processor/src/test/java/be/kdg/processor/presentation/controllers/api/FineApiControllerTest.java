package be.kdg.processor.presentation.controllers.api;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import be.kdg.processor.persistence.FineRepository;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.modelservices.VehicleService;
import org.h2.engine.Session;
import org.hibernate.SessionFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FineApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FineService fineService;
    @Autowired
    private VehicleService vehicleService;

    @Test
    public void testLoadFines() throws Exception {
        vehicleService.extractPlateInfo(new LicensePlateInfo("3-ABC-123", "NaN", 3));
        fineService.createEmissionFine(3, 3,
                new CameraMessage(3, "3-ABC-123", LocalDateTime.now()),
                "3-ABC-123");

        mockMvc.perform(get("/api/fines/" + findFineByPlate("3-ABC-123"))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testApproveFine() throws Exception {
        vehicleService.extractPlateInfo(new LicensePlateInfo("4-ABC-123", "NaN", 3));
        fineService.createEmissionFine(3, 3,
                new CameraMessage(3, "4-ABC-123", LocalDateTime.now()),
                "4-ABC-123");

        mockMvc.perform(put("/api/fines/approve/" + findFineByPlate("4-ABC-123"))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        assertTrue(fineService.getFine(findFineByPlate("4-ABC-123")).isApproved());
    }

    @Test
    public void testUpdateAmount() throws Exception {
        vehicleService.extractPlateInfo(new LicensePlateInfo("5-ABC-123", "NaN", 3));
        fineService.createEmissionFine(3, 3,
                new CameraMessage(3, "5-ABC-123", LocalDateTime.now()),
                "5-ABC-123");
        final String jsonString = "{\n" +
                "  \"amount\": 1000.0,\n" +
                "  \"motivation\": \"Changed for now reason :))\"\n" +
                "}";

        mockMvc.perform(put("/api/fines/updateAmount/" + findFineByPlate("5-ABC-123"))
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
                .andExpect(status().isOk());
        assertFalse(fineService.getFine(findFineByPlate("5-ABC-123")).getChangeAmountMotivation().equalsIgnoreCase("default"));
    }

    private int findFineByPlate(String plate)  {
        return fineService.getFines().stream()
                .filter(fine -> fine instanceof EmissionFine)
                .filter(fine -> ((EmissionFine) fine).getEmmisionMessage().getLicensePlate().equalsIgnoreCase(plate))
                .findAny().get().getFineId();
    }
}