package org.bedu.proyecto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.repository.ClientRepository;
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

    
}
