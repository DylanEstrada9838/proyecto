package org.bedu.proyecto.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bedu.proyecto.dto.user.UpdateUserDTO;
import org.bedu.proyecto.dto.user.UserDTO;
import org.bedu.proyecto.exception.user.PasswordNotAllowed;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.model.User;
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
class UserServiceTest {
    @MockBean
    UserRepository repository;

    @Autowired
    UserService service;

    @Test
  @DisplayName("Service should be injected")
  void smokeTest() {
    assertNotNull(service);
  }

  @Test
  @DisplayName("Service should return a list of Users")
  void findAllTest (){
    List<User> users = new LinkedList<>();
    User user = User.builder()
    .email("test@mail.com")
    .id(999L)
    .password("123").build();
    users.add(user);

    when(repository.findAll()).thenReturn(users);

    List<UserDTO> userDTOs= service.findAll();

    assertNotNull(userDTOs);
    assertTrue(userDTOs.size() > 0);
    assertEquals(user.getId(), userDTOs.get(0).getId());
    assertEquals(user.getEmail(), userDTOs.get(0).getEmail());
    assertEquals(user.getPassword(), userDTOs.get(0).getPassword());

  }

  @Test
  @DisplayName("Service should return an User")
  void findByIdTest() throws UserNotFoundException{
    User user = User.builder()
    .id(99L)
    .email("test@mail.com")
    .password("123")
    .build();

    when(repository.findById(anyLong())).thenReturn(Optional.of(user));

    UserDTO result = service.findById(99L);

    assertNotNull(result);
    assertEquals(user.getId(), result.getId());
    assertEquals(user.getEmail(), result.getEmail());
    assertEquals(user.getPassword(), result.getPassword());
    
    

  }
  @Test
  @DisplayName("Service shoul update an User")
  void updateTest() throws UserNotFoundException, PasswordNotAllowed{
    UpdateUserDTO dto = UpdateUserDTO.builder()
    .password("12345")
    .build();

    User user = User.builder()
    .id(99L)
    .email("test@mail.com")
    .password("123")
    .build();

    when(repository.findById(anyLong())).thenReturn(Optional.of(user));

    service.update(99L, dto);

    assertEquals(dto.getPassword(), user.getPassword());
    verify(repository,times(1)).save(user);


  }
  @Test
  @DisplayName("Service should delete an User")
  void deleteByIdTest() throws UserNotFoundException{
    User user = User.builder()
    .id(99L)
    .email("test@mail.com")
    .password("123")
    .build();
    when(repository.findById(anyLong())).thenReturn(Optional.of(user));
    service.deleteById(99L);
    verify(repository,times(1)).delete(user);
  
  }
  @Test
  @DisplayName("")
  void retrieveUserIdTest() {
    
  }
}
