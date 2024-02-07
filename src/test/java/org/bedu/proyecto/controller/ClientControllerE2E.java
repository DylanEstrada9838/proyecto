package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.model_enums.Role;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import java.util.List;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClientControllerE2E {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientRepository repository;
    @Autowired
    private UserRepository userRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    @Transactional
    void setup() {
        // repository.deleteAll();
        User user1 = userRepository.save(User.builder()
                .email("test1@mail.com")
                .id(99L)
                .password("123")
                .role(Role.ROLE_USER)
                .build());
        User user2 = userRepository.save(User.builder()
                .email("test2@mail.com")
                .id(999L)
                .role(Role.ROLE_USER)
                .password("123")
                .build());
                List<User> users = userRepository.findAll();
        Client client1 = repository.save(Client.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .user(users.get(0))
                .build());
        Client client2 = repository.save(Client.builder()
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("123456789")
                .user(users.get(1))
                .build());
    }

    @AfterEach
    void afterSetup() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /clients should return an empty list")
    void emptyListTest() throws Exception {

        MvcResult result = mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("[]", content);
    }

    @Test
    @DisplayName("GET /client should return a list of client")
    void findAllTest() throws Exception {
       

        MvcResult result = mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        // Creamos una referencia del tipo al que se va a convertir el JSON
        TypeReference<List<ClientDTO>> listTypeReference = new TypeReference<List<ClientDTO>>() {
        };

        // Convertimos el JSON a un objeto de Java
        List<ClientDTO> response = mapper.readValue(content, listTypeReference);

        // Hacemos las verificaciones basadas en los objetos
        assertTrue(response.size() == 2);
        
    }




    // @Test
    // @DisplayName("POST /clients should update a client")
    // void updateTest() throws Exception {
    //     List<Client> clients = repository.findAll();
    //     Long id = clients.get(0).getId();
    //     mockMvc.perform(put("/clients/{id}",id).contentType("application/json").content("{\"name\":\"test01\"}"))
    //             .andExpect(status().isNoContent())
    //             .andReturn();

    //     List<Client> client = repository.findAll();

    //     assertEquals(client.get(0).getName(),"test01");
    // }

}
