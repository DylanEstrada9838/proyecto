package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.bedu.proyecto.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.bedu.proyecto.dto.user.UpdateUserDTO;
import org.bedu.proyecto.dto.user.UserDTO;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.user.PasswordNotAllowed;
import org.bedu.proyecto.exception.user.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTest {
    @MockBean
    UserService service;
    @Autowired
    UserController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should return a list of users")
    void findAllTest() {
        List<UserDTO> expectedUserDTOs = new LinkedList<>();

        UserDTO user = UserDTO.builder()
                .id(999)
                .email("testmail@gmail.com")
                .password("1234")
                .build();
        expectedUserDTOs.add(user);

        when(service.findAll()).thenReturn(expectedUserDTOs);

        //Act
        List<UserDTO> actualUserDTOs = controller.findAll();
        //Assert
        assertEquals(user.getId(), actualUserDTOs.get(0).getId());
        assertEquals(user.getEmail(), actualUserDTOs.get(0).getEmail());
        assertEquals(user.getPassword(), actualUserDTOs.get(0).getPassword());
        assertTrue(actualUserDTOs.size()>0);
        

    }
    @Test
    @DisplayName("Controller should return a user")
    void findByIdTest() throws UserNotFoundException, UnauthorizedAction {
         // Mocking behavior of service.retrieveUserId()
        when(service.retrieveUserId()).thenReturn(1L);

        // Mocking behavior of service.findById()
        UserDTO userDTO = UserDTO.builder()
        .id(1L)
        .email("mail@mail.com")
        .password("123")
        .build();
        when(service.findById(1L)).thenReturn(userDTO);

        // Call the controller method
        UserDTO response = controller.findById(1L);
        assertEquals(userDTO, response);

        // Verify that the service methods were called
        verify(service).retrieveUserId();
        verify(service).findById(1L);

    }
   @Test
    @DisplayName("Controller should delete a user")
     void deleteByIdByIdTest() throws UserNotFoundException, UnauthorizedAction {
        when(service.retrieveUserId()).thenReturn(1L);
        
        controller.deleteById(1L);
        
        verify(service, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Controller should update a user")
    void updateTest() throws UserNotFoundException, PasswordNotAllowed, UnauthorizedAction{
        when(service.retrieveUserId()).thenReturn(999L);

        UpdateUserDTO dto = UpdateUserDTO.builder()
        .password("123").build();
        controller.update(999L, dto);
        verify(service, times(1)).update(999L, dto);
    }

    
}
