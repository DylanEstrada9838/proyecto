package org.bedu.proyecto.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ClientRepositoryTest {

  @Autowired
  private ClientRepository repository;

  @Autowired
  private TestEntityManager manager;

  @Test
  @DisplayName("Repository should be injected")
  void smokeTest() {
    assertNotNull(repository);
  }

  @Test
  @DisplayName("Repository should find all clients")
  void findAllTest() {

    Client client1 = new Client();
    Client client2 = new Client();

    manager.persist(client1);
    manager.persist(client2);

    List<Client> result = repository.findAll();

    assertTrue(result.size() == 2);
  }

  @Test
  @DisplayName("Repository should find client by user")
  void findByUserTest() {

    User user1 = new User();
    User user2 = new User();

    Client client1 = new Client();
    client1.setUser(user1);

    Client client2 = new Client();
    client2.setUser(user2);

    manager.persist(user1);
    manager.persist(user2);
    manager.persist(client1);
    manager.persist(client2);

    Optional<Client> result = repository.findByUser(user1);

    assertTrue(result.isPresent());
    assertTrue(result.get().getUser().equals(user1));
  }
}
