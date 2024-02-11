package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.bedu.proyecto.dto.supplier.SupplierDTO;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Role;
import org.bedu.proyecto.repository.SupplierRepository;
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
class SupplierControllerE2E {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SupplierRepository repository;
    @Autowired
    private UserRepository userRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unused")
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
        Supplier supplier1 = repository.save(Supplier.builder()
                .businessName("Business 999")
                .phone("121212")
                .address("city A street B")
                .user(users.get(0))
                .build());
        Supplier supplier2 = repository.save(Supplier.builder()
                .businessName("Business 9994")
                .phone("1212123")
                .address("city A street B")
                .user(users.get(1))
                .build());
    }

    @AfterEach
    void afterSetup() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /suppliers should return an empty list")
    void emptyListTest() throws Exception {
        repository.deleteAll();
        MvcResult result = mockMvc.perform(get("/suppliers"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("[]", content);
    }

    @Test
    @DisplayName("GET /supplier should return a list of supplier")
    void findAllTest() throws Exception {

        MvcResult result = mockMvc.perform(get("/suppliers"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        // Creamos una referencia del tipo al que se va a convertir el JSON
        TypeReference<List<SupplierDTO>> listTypeReference = new TypeReference<List<SupplierDTO>>() {
        };

        // Convertimos el JSON a un objeto de Java
        List<SupplierDTO> response = mapper.readValue(content, listTypeReference);

        // Hacemos las verificaciones basadas en los objetos
        assertTrue(response.size() == 2);

    }

}
