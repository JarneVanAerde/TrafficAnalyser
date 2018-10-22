package be.kdg.processor.presentation.controllers.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserWebControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showLogin() throws Exception {
        mockMvc.perform(get("/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void showMenu() throws Exception {
        mockMvc.perform(get("/user/menu")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}