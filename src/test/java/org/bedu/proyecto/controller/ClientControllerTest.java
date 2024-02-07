package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.bedu.proyecto.dto.address.AddressDTO;
import org.bedu.proyecto.dto.address.CreateAddressDTO;
import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.dto.client.CreateClientDTO;
import org.bedu.proyecto.dto.client.UpdateClientDTO;
import org.bedu.proyecto.dto.servicerequest.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.exception.address.AddressNotAssignedToClient;
import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.client.ClientNotFoundException;

import org.bedu.proyecto.exception.client.ClientUserAlreadyExist;
import org.bedu.proyecto.exception.request.ServiceRequestCreateNotAllowed;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
 class ClientControllerTest {
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
    void smokeTest() {
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

        controller.delete(999L);

        verify(service, times(1)).delete(999L);

    }

    @Test
    @DisplayName("Controller should add a ServiceRequest to a Client")
    void addServiceRequestTest() throws ClientNotFoundException, ServiceNotFoundException,
            ServiceRequestCreateNotAllowed, AddressNotAssignedToClient, AddressNotFound, ServiceNotAssignedException {
        CreateServiceRequestDTO createDTO = CreateServiceRequestDTO.builder()
                .addressId(99L)
                .description("casa")
                .status(StatusRequest.OPEN)
                .urgency(Urgency.HIGH)
                .serviceId(99L)
                .build();
        controller.addServiceRequest(99L, createDTO);
        verify(serviceRequestService, times(1)).save(99L, createDTO);

    }

    @Test
    @DisplayName("Controller should return a list of Service Request given a clientId")
    void findAllServiceRequestByClientTest() throws ClientNotFoundException {
        List<ServiceRequestDTO> serviceRequestDTOs = new LinkedList<>();

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
        serviceRequestDTOs.add(dto);
        when(serviceRequestService.findAllByClient(anyLong())).thenReturn(serviceRequestDTOs);

        List<ServiceRequestDTO> result = controller.findAllServiceRequestByClient(99L);

        assertTrue(result.size() > 0);
        assertEquals(dto.getAddressId(), result.get(0).getAddressId());
        assertEquals(dto.getClientId(), result.get(0).getClientId());
        assertEquals(dto.getCreatedAt(), result.get(0).getCreatedAt());
        assertEquals(dto.getDescription(), result.get(0).getDescription());
        assertEquals(dto.getId(), result.get(0).getId());
        assertEquals(dto.getStatus(), result.get(0).getStatus());
        assertEquals(dto.getUrgency(), result.get(0).getUrgency());
        assertEquals(dto.getServiceId(), result.get(0).getServiceId());
    }

    @Test
    @DisplayName("Controller should save rating")
    void addRatingTest() {

    }

    @Test
    @DisplayName("Controller should save rating")
    void addAddressTest() throws ClientNotFoundException {
        CreateAddressDTO createDTO = CreateAddressDTO.builder()
                .city("city A")
                .line1("streetA")
                .postalCode("12345")
                .state("state A")
                .build();

        controller.addAddress(99L, createDTO);
        verify(addressService, times(1)).save(99L, createDTO);

    }

    @Test
    @DisplayName("Controller should save rating")
    void findAllAddresses() throws ClientNotFoundException {
        List<AddressDTO> addressDTOs = new LinkedList<>();

        AddressDTO addressDTO = AddressDTO.builder()
                .city("city A")
                .line1("streetA")
                .postalCode("12345")
                .state("state A")
                .build();
                addressDTOs.add(addressDTO);
        when(addressService.findAllByClient(anyLong())).thenReturn(addressDTOs);

        List<AddressDTO> result = controller.findAllAddresses(99L);

        assertTrue(result.size()>0);
        assertEquals(addressDTO.getId(), result.get(0).getId());
        assertEquals(addressDTO.getCity(), result.get(0).getCity());
        assertEquals(addressDTO.getLine1(), result.get(0).getLine1());
        assertEquals(addressDTO.getPostalCode(), result.get(0).getPostalCode());
        assertEquals(addressDTO.getState(), result.get(0).getState());
        verify(addressService, times(1)).findAllByClient(99L);

    }

}
