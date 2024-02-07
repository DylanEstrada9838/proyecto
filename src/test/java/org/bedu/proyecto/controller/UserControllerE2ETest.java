package org.bedu.proyecto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.proyecto.dto.user.UpdateUserDTO;
import org.bedu.proyecto.dto.user.UserDTO;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.user.PasswordNotAllowed;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.service.UserService;
import org.junit.jupiter.api.*;
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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(userService);
    }

    @Test
    @DisplayName("Controller should return a list of users")
    void findAllTest() throws Exception {
        List<UserDTO> expectedUserDTOs = new LinkedList<>();

        UserDTO user = UserDTO.builder()
                .id(999)
                .email("testmail@gmail.com")
                .password("1234")
                .build();
        expectedUserDTOs.add(user);

        when(userService.findAll()).thenReturn(expectedUserDTOs);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        List<UserDTO> actualUserDTOs = objectMapper.readValue(content, new LinkedList<UserDTO>().getClass());

        assertEquals(user.getId(), actualUserDTOs.get(0).getId());
        assertEquals(user.getEmail(), actualUserDTOs.get(0).getEmail());
        assertEquals(user.getPassword(), actualUserDTOs.get(0).getPassword());
        assertTrue(actualUserDTOs.size() > 0);
    }

    @Test
    @DisplayName("Controller should return a user")
    void findByIdTest() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("mail@mail.com")
                .password("123")
                .build();

        when(userService.findById(1L)).thenReturn(userDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        UserDTO actualUserDTO = objectMapper.readValue(content, UserDTO.class);

        assertEquals(userDTO, actualUserDTO);

        verify(userService).findById(1L);
    }

    @Test
    @DisplayName("Controller should delete a user")
    void deleteByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Controller should update a user")
    void updateTest() throws Exception {
        UpdateUserDTO dto = UpdateUserDTO.builder()
                .password("123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService, times(1)).update(1L, dto);
    }
}