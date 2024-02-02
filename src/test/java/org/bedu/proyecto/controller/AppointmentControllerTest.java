package org.bedu.proyecto.controller;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bedu.proyecto.service.AppointmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AppointmentControllerTest {
    @MockBean
    AppointmentService service;
    @Autowired
    AppointmentController controller;

    @Test
    @DisplayName("Controller should be injected")
    public void smokeTest(){
        assertNotNull(controller);
    }
}
