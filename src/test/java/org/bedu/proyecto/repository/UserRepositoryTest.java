package org.bedu.proyecto.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.List;

import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Repository should save an User")
    void saveTest() {

        User user = repository.save(User.builder()
                .email("test@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .build());

        assertNotNull(user);
        assertTrue(user.getId() > 0);
    }

    @Test
    @DisplayName("Repository should find an User")
    void findByIdTest() {
        User savedUser = repository.save(User.builder()
                .email("test@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .build());

        Optional<User> user = repository.findById(savedUser.getId());

        assertTrue(user.isPresent());
        assertEquals(savedUser.getEmail(), user.get().getEmail());
        assertEquals(savedUser.getPassword(), user.get().getPassword());
    }

    @Test
    @DisplayName("Repository should  find all Users")
    void findAllTest() {
        User savedUser = User.builder()
                .email("test@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .build();
        User savedUser2 = User.builder()
                .email("test2@gmail.com")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();
        repository.save(savedUser);
        repository.save(savedUser2);

        List<User> users = repository.findAll();

        assertFalse(users.isEmpty());
        assertTrue(users.size() == 2);

    }

    @Test
    @DisplayName("Repository should  find an User by Email")
    void findByEmailTest() {
        User savedUser = User.builder()
                .email("test@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .build();
        repository.save(savedUser);

        Optional<User> user = repository.findByEmail("test@gmail.com");

        assertTrue(user.isPresent());
        assertEquals(savedUser.getEmail(), user.get().getEmail());

    }

    @Test
    @DisplayName("Repository should update an User")
    void updateTest() {
        User savedUser = User.builder()
                .email("test@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .build();
        repository.save(savedUser);

        User user = repository.findById(savedUser.getId()).get();

        user.setEmail("updated@gmail.com");
        User updatedUser = repository.save(user);

        assertNotNull(updatedUser);
        assertEquals(updatedUser.getEmail(), "updated@gmail.com");
    }

    @Test
    @DisplayName("Repository should update an User")
    void deleteTest() {
        User savedUser = User.builder()
                .email("test@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .build();
        repository.save(savedUser);

        repository.deleteById(savedUser.getId());
        Optional<User> user = repository.findById(savedUser.getId());

        assertTrue(user.isEmpty());
    }
}
