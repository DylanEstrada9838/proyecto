package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.repository.ClientRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClientControllerE2E {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository repository;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        repository.deleteAll();
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
        Client client1 = Client.builder()
                .id(999)
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build();
        Client client2 = Client.builder()
                .id(999)
                .name("test2")
                .lastName("test2")
                .age(20)
                .gender(Gender.FEMALE)
                .phone("12345678")
                .build();

        repository.save(client1);
        repository.save(client2);

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
        assertEquals(client1.getId(), response.get(0).getId());
        assertEquals(client2.getId(), response.get(1).getId());
        assertEquals(client1.getAge(), response.get(0).getAge());
        assertEquals(client2.getAge(), response.get(1).getAge());
        assertEquals(client1.getGender(), response.get(0).getGender());
        assertEquals(client2.getGender(), response.get(1).getGender());
        assertEquals(client1.getName(), response.get(0).getName());
        assertEquals(client2.getName(), response.get(1).getName());
        assertEquals(client1.getLastName(), response.get(0).getLastName());
        assertEquals(client2.getLastName(), response.get(1).getLastName());
        assertEquals(client1.getPhone(), response.get(0).getPhone());
        assertEquals(client1.getPhone(), response.get(0).getPhone());
    }

    


}