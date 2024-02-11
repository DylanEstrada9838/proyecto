package org.bedu.proyecto.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.List;

import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.model_enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ClientRepositoryTest {
    @Autowired
    ClientRepository repository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(repository);
    }

    @SuppressWarnings("unused")
    @BeforeEach
    void setup() {
        User user = userRepository.save(User.builder()
                .email("test@gmail.com")
                .password("123")
                .role(Role.ROLE_USER)
                .build());
    }

    @Test
    @DisplayName("Repository should save an Client")
    void saveTest() {

        Client client = repository.save(Client.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build());

        assertNotNull(client);
        assertTrue(client.getId() > 0);
    }

    @Test
    @DisplayName("Repository should find an Client")
    void findByIdTest() {
        Client savedClient = repository.save(Client.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build());

        Optional<Client> Client = repository.findById(savedClient.getId());

        assertTrue(Client.isPresent());
        assertEquals(savedClient.getName(), Client.get().getName());
        assertEquals(savedClient.getLastName(), Client.get().getLastName());
        assertEquals(savedClient.getAge(), Client.get().getAge());
        assertEquals(savedClient.getGender(), Client.get().getGender());
        assertEquals(savedClient.getPhone(), Client.get().getPhone());
    }

    @Test
    @DisplayName("Repository should  find all Clients")
    void findAllTest() {
        Client savedClient = repository.save(Client.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build());
        Client savedClient2 = repository.save(Client.builder()
                .name("test2")
                .lastName("test2")
                .age(30)
                .gender(Gender.MALE)
                .phone("123456789")
                .build());
        repository.save(savedClient);
        repository.save(savedClient2);

        List<Client> Clients = repository.findAll();

        assertFalse(Clients.isEmpty());
        assertTrue(Clients.size() == 2);

    }

    @Test
    @DisplayName("Repository should  find an Client by Email")
    void findByUserTest() {
        List<User> users = userRepository.findAll();
        User user = userRepository.findById(users.get(0).getId()).get();

        Client savedClient = repository.save(Client.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .user(user)
                .build());

        Optional<Client> Client = repository.findByUser(user);

        assertTrue(Client.isPresent());
        assertEquals(savedClient.getName(), Client.get().getName());
        assertEquals(savedClient.getLastName(), Client.get().getLastName());
        assertEquals(savedClient.getAge(), Client.get().getAge());
        assertEquals(savedClient.getGender(), Client.get().getGender());
        assertEquals(savedClient.getPhone(), Client.get().getPhone());

    }

    @Test
    @DisplayName("Repository should update an Client")
    void updateTest() {
        Client savedClient = repository.save(Client.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build());

        Client Client = repository.findById(savedClient.getId()).get();

        Client.setName("updated");
        Client.setLastName("updated");
        Client.setGender(Gender.FEMALE);
        Client.setPhone("updated");
        Client.setAge(99);
        Client updatedClient = repository.save(Client);

        assertNotNull(updatedClient);
        assertEquals(updatedClient.getName(), "updated");
        assertEquals(updatedClient.getAge(),99);
        assertEquals(updatedClient.getGender(),Gender.FEMALE);
        assertEquals(updatedClient.getPhone(),"updated");
        assertEquals(updatedClient.getLastName(),"updated");

    }

    @Test
    @DisplayName("Repository should update an Client")
    void deleteTest() {
        Client savedClient = repository.save(Client.builder()
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build());

        repository.deleteById(savedClient.getId());
        Optional<Client> Client = repository.findById(savedClient.getId());

        assertTrue(Client.isEmpty());
    }
}
