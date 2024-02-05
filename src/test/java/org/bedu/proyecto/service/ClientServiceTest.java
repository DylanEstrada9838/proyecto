package org.bedu.proyecto.service;

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
import java.util.Optional;

import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.dto.client.CreateClientDTO;
import org.bedu.proyecto.dto.client.UpdateClientDTO;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.client.ClientUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.UserRepository;
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
class ClientServiceTest {
    @MockBean
    ClientRepository repository;

    @Autowired
    ClientService service;

    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Service should return a list of clients")
    void findAllTest() {
        List<Client> clients = new LinkedList<>();
        Client client = Client.builder()
                .id(999)
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build();

        clients.add(client);

        when(repository.findAll()).thenReturn(clients);

        List<ClientDTO> clientDTOs = service.findAll();
        assertNotNull(clientDTOs);
        assertTrue(clientDTOs.size() > 0);
        assertEquals(client.getId(), clientDTOs.get(0).getId());
        assertEquals(client.getName(), clientDTOs.get(0).getName());
        assertEquals(client.getLastName(), clientDTOs.get(0).getLastName());
        assertEquals(client.getPhone(), clientDTOs.get(0).getPhone());
        assertEquals(client.getAge(), clientDTOs.get(0).getAge());
        assertEquals(client.getGender(), clientDTOs.get(0).getGender());

    }

    @Test
    @DisplayName("Service should return a client")
    void findByIdTest() throws ClientNotFoundException {
        Client client = Client.builder()
                .id(999)
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build();

        when(repository.findById(anyLong())).thenReturn(Optional.of(client));

        ClientDTO clientDTO = service.findById(99L);

        assertNotNull(clientDTO);
        assertEquals(client.getId(), clientDTO.getId());
        assertEquals(client.getName(), clientDTO.getName());
        assertEquals(client.getLastName(), clientDTO.getLastName());
        assertEquals(client.getPhone(), clientDTO.getPhone());
        assertEquals(client.getAge(), clientDTO.getAge());
        assertEquals(client.getGender(), clientDTO.getGender());

    }

    @Test
    @DisplayName("Service should save a client")
    void saveTest() throws UserNotFoundException, ClientUserAlreadyExist {
        CreateClientDTO createDTO = CreateClientDTO.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build();
        Client client = Client.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build();
        User user = User.builder()
                .id(999L)
                .email("test@mail.com")
                .password("123")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(repository.save(any(Client.class))).thenReturn(client);

        ClientDTO clientDTO = service.save(createDTO, 999L);

        assertNotNull(clientDTO);
        assertEquals(client.getId(), clientDTO.getId());
        assertEquals(client.getName(), clientDTO.getName());
        assertEquals(client.getLastName(), clientDTO.getLastName());
        assertEquals(client.getPhone(), clientDTO.getPhone());
        assertEquals(client.getAge(), clientDTO.getAge());
        assertEquals(client.getGender(), clientDTO.getGender());
    }

    @Test
    @DisplayName("Service should update a client")
    void updateTest() throws ClientNotFoundException {
        UpdateClientDTO clientDTO = UpdateClientDTO.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build();

        Client client = Client.builder()
                .id(99L)
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("2222222222")
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(client));

        service.update(99L, clientDTO);

        assertEquals(client.getName(), clientDTO.getName());
        assertEquals(client.getLastName(), clientDTO.getLastName());
        assertEquals(client.getPhone(), clientDTO.getPhone());
        assertEquals(client.getAge(), clientDTO.getAge());
        assertEquals(client.getGender(), clientDTO.getGender());
        verify(repository, times(1)).save(client);
    }

    @Test
    @DisplayName("Service should delete a client")
    void deleteTest() throws ClientNotFoundException {
        Client client = Client.builder()
                .id(99L)
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("2222222222")
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(client));
        service.delete(99L);
        verify(repository, times(1)).delete(client);

    }
}