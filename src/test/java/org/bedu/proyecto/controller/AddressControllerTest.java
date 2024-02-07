package org.bedu.proyecto.controller;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.bedu.proyecto.dto.address.UpdateAddressDTO;
import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.address.UpdateOrDeleteNotAllowed;
import org.bedu.proyecto.service.AddressService;
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
class AddressControllerTest {
    @MockBean
    AddressService service;
    @Autowired
    AddressController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest(){
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should update an address")
    void updateTest() throws AddressNotFound, UpdateOrDeleteNotAllowed{
        UpdateAddressDTO dto = UpdateAddressDTO.builder()
        .city("city A")
        .line1("streetA")
        .postalCode("12345")
        .state("state A")
        .build();
        controller.update(99L, dto);

        verify(service,times(1)).update(99L, dto);
    }

    @Test
    @DisplayName("Controller should delete an address")
    void deleteTest() throws AddressNotFound, UpdateOrDeleteNotAllowed{
        controller.delete(99L);

        verify(service,times(1)).delete(99L);
    }
}
