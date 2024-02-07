package org.bedu.proyecto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.proyecto.dto.address.UpdateAddressDTO;
import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.address.UpdateOrDeleteNotAllowed;
import org.bedu.proyecto.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(addressService);
    }

    @Test
    @DisplayName("Controller should update an address")
    void updateTest() throws Exception {
        UpdateAddressDTO dto = UpdateAddressDTO.builder()
                .city("city A")
                .line1("streetA")
                .postalCode("12345")
                .state("state A")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/addresses/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(addressService, times(1)).update(99L, dto);
    }

    @Test
    @DisplayName("Controller should delete an address")
    void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/addresses/99"))
                .andExpect(status().isOk());

        verify(addressService, times(1)).delete(99L);
    }
}