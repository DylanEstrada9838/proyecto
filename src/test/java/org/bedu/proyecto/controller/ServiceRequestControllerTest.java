package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;
import org.bedu.proyecto.service.ServiceRequestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class ServiceRequestControllerTest {
    @MockBean
    ServiceRequestService service;
    @Autowired
    ServiceRequestController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should return a service request.")
    void findByIdTest() throws ServiceRequestNotFound {
        ServiceRequestDTO dto = ServiceRequestDTO.builder()
                .addressId(99L)
                .clientId(99L)
                .createdAt(Instant.now())
                .description("casa")
                .id(99)
                .status(StatusRequest.OPEN)
                .urgency(Urgency.HIGH)
                .serviceId(99L)
                .build();

        when(service.findById(anyLong())).thenReturn(dto);
        ServiceRequestDTO result = controller.findById(99L);
        assertNotNull(result);
        assertEquals(dto.getAddressId(), result.getAddressId());
        assertEquals(dto.getClientId(), result.getClientId());
        assertEquals(dto.getCreatedAt(), result.getCreatedAt());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getUrgency(), result.getUrgency());
        assertEquals(dto.getServiceId(), result.getServiceId());

    }
}
