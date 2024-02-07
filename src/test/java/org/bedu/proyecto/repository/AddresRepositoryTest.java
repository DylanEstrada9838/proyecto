package org.bedu.proyecto.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bedu.proyecto.model.Address;
import org.bedu.proyecto.model.Client;
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

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AddressRepositoryTest {

  @Autowired
  private AddressRepository repository;

  @Autowired
  private TestEntityManager manager;

  @Test
  @DisplayName("Repository should be injected")
  void smokeTest() {
    assertNotNull(repository);
  }

  @Test
  @DisplayName("Repository should find addresses by client")
  void findAllByClientTest() {

    Client client1 = new Client();
    Client client2 = new Client();

    Address address1 = new Address();
    Address address2 = new Address();
    Address address3 = new Address();

    // Set client for addresses
    address1.setClient(client1);
    address2.setClient(client1);
    address3.setClient(client2);

    // Crea los registros en la base de datos de prueba (h2)
    manager.persist(client1);
    manager.persist(client2);
    manager.persist(address1);
    manager.persist(address2);
    manager.persist(address3);

    List<Address> result = repository.findAllByClient(client1);

    assertTrue(result.size() == 2);
  }
}
