// package org.bedu.proyecto.repository;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.bedu.proyecto.model.Client;
// import org.bedu.proyecto.model.User;
// import org.bedu.proyecto.model_enums.Gender;
// import org.bedu.proyecto.model_enums.Role;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import jakarta.transaction.Transactional;

// import java.util.List;
// import java.util.Optional;

// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = Replace.NONE)
// class ClientRepositoryTest {

//     @Autowired
//     private ClientRepository repository;

//     @Autowired
//     private UserRepository userRepository;
    

//     @Autowired
//     private TestEntityManager manager;

//     @Test
//     @DisplayName("Repository should be injected")
//     void smokeTest() {
//         assertNotNull(repository);
//     }

//      @BeforeEach
//     @Transactional
//     void setup() {
//         // repository.deleteAll();
//         User user1 = manager.persist(User.builder()
//                 .email("test1@mail.com")
//                 .id(99L)
//                 .password("123")
//                 .role(Role.ROLE_USER)
//                 .build());
//         User user2 =manager.persist(User.builder()
//                 .email("test2@mail.com")
//                 .id(999L)
//                 .password("123")
//                 .role(Role.ROLE_USER)
//                 .build());

//     }
//     @AfterEach
//     void after(){
//        userRepository.deleteAll();
//         repository.deleteAll();
//     }


//     @Test
//     @DisplayName("Repository should find all clients")
//     void findAllTest() {
//         List<User> users = userRepository.findAll();
//         Client client1 = repository.save(Client.builder()
//                 .name("test")
//                 .lastName("test")
//                 .age(30)
//                 .gender(Gender.MALE)
//                 .phone("12345678")
//                 .user(users.get(0))
//                 .build());
//         Client client2 = repository.save(Client.builder()
//                 .name("test2")
//                 .lastName("test2")
//                 .age(20)
//                 .gender(Gender.FEMALE)
//                 .phone("123456789")
//                 .user(users.get(1))
//                 .build());

//         manager.persist(client1);
//         manager.persist(client2);

//         List<Client> result = repository.findAll();

//         assertTrue(result.size() == 2);
//     }

//     @Test
//     @DisplayName("Repository should find client by user")
//     void findByUserTest() {

//         User user1 = new User();
//         User user2 = new User();

//         Client client1 = Client.builder()
//                 .id(999)
//                 .name("test")
//                 .lastName("test")
//                 .age(30)
//                 .gender(Gender.MALE)
//                 .phone("12345678")
//                 .user(user1)
//                 .build();
//         Client client2 = Client.builder()
//                 .id(99)
//                 .name("test")
//                 .lastName("test")
//                 .age(30)
//                 .gender(Gender.MALE)
//                 .phone("12345678")
//                 .user(user2)
//                 .build();

//         manager.persist(user1);
//         manager.persist(user2);
//         manager.persist(client1);
//         manager.persist(client2);

//         Optional<Client> result = repository.findByUser(user1);

//         assertTrue(result.isPresent());
//         assertTrue(result.get().getUser().equals(user1));
//     }
// }
