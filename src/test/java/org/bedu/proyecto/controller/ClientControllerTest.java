package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.dto.client.CreateClientDTO;
import org.bedu.proyecto.dto.client.UpdateClientDTO;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.client.ClientNotFoundException;

import org.bedu.proyecto.exception.client.ClientUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.service.AddressService;
import org.bedu.proyecto.service.ClientService;
import org.bedu.proyecto.service.RatingService;
import org.bedu.proyecto.service.ServiceRequestService;
import org.bedu.proyecto.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClientControllerTest {
    @MockBean
    ClientService service;
    @Autowired
    ClientController controller;
    @MockBean
    UserService userService;
    @MockBean
    ServiceRequestService serviceRequestService;
    @MockBean
    RatingService ratingService;
    @MockBean
    AddressService addressService;

    @Test
    @DisplayName("Controller should be injected")
    public void smokeTest() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should return a list of clients")
    void findAllTest() {
        List<ClientDTO> expectedClientDTOs = new LinkedList<>();

        ClientDTO client = ClientDTO.builder()
                .id(99L)
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("2222222222")
                .build();
        expectedClientDTOs.add(client);

        when(service.findAll()).thenReturn(expectedClientDTOs);

        // Act
        List<ClientDTO> actualClientDTOs = controller.findAll();
        // Assert
        assertNotNull(actualClientDTOs);
        assertEquals(expectedClientDTOs, actualClientDTOs);
        assertTrue(actualClientDTOs.size() > 0);
        assertEquals(client.getId(), actualClientDTOs.get(0).getId());
        assertEquals(client.getName(), actualClientDTOs.get(0).getName());
        assertEquals(client.getPhone(), actualClientDTOs.get(0).getPhone());

    }

    @Test
    @DisplayName("Controller should return a client")
    void findByIdTest() throws ClientNotFoundException, UnauthorizedAction {
        // Mocking behavior of service.findById()
        ClientDTO clientDTO = ClientDTO.builder()
                .id(99L)
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("2222222222")
                .build();
        when(service.findById(1L)).thenReturn(clientDTO);

        // Call the controller method
        ClientDTO response = controller.findById(1L);
        assertEquals(clientDTO.getId(), response.getId());
        assertEquals(clientDTO.getPhone(), response.getPhone());
        assertEquals(clientDTO.getName(), response.getName());
        assertEquals(clientDTO.getLastName(), response.getLastName());
        assertEquals(clientDTO.getGender(), response.getGender());
        assertEquals(clientDTO.getAge(), response.getAge());

        // Verify that the service methods were called
        verify(service).findById(1L);

    }

    @Test
    @DisplayName("Controller should save a client")
    void saveTest() throws UserNotFoundException, ClientUserAlreadyExist {
        // Arrange
        CreateClientDTO createDTO = CreateClientDTO.builder()
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("2222222222")
                .build();

        ClientDTO expectedDto = ClientDTO.builder()
                .id(99L)
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("2222222222")
                .build();

        when(service.save(any(CreateClientDTO.class), anyLong())).thenReturn(expectedDto);
        // Act
        ClientDTO response = controller.save(createDTO);
        // Assert
        assertNotNull(response);
        assertEquals(expectedDto.getPhone(), response.getPhone());
        assertEquals(expectedDto.getName(), response.getName());
        assertEquals(expectedDto.getLastName(), response.getLastName());
        assertEquals(expectedDto.getGender(), response.getGender());
        assertEquals(expectedDto.getAge(), response.getAge());

    }

    @Test
    @DisplayName("Controller should update a Client")
    void updateTest() throws ClientNotFoundException, UnauthorizedAction, UserNotFoundException {
        when(userService.retrieveUserId()).thenReturn(999L);

        UpdateClientDTO dto = UpdateClientDTO.builder()
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("2222222222")
                .build();
        controller.update(999L, dto);
        verify(service, times(1)).update(999L, dto);
    }

    @Test
    @DisplayName("Controller should delete a Client")
    void deleteTest() throws ClientNotFoundException, UnauthorizedAction, UserNotFoundException {
        when(userService.retrieveUserId()).thenReturn(999L);

        controller.delete(999L);

        verify(service, times(1)).delete(999L);

    }

    @Test
    @DisplayName("Controller should add a ServiceRequest to a Client")
    void addServiceRequestTest(){
        
    }

}
