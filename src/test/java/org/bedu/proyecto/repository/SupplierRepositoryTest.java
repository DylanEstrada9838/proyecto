package org.bedu.proyecto.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.List;

import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SupplierRepositoryTest {
    @Autowired
    SupplierRepository repository;
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
    @DisplayName("Repository should save an Supplier")
    void saveTest() {

        Supplier supplier = repository.save(Supplier.builder()
                .businessName("Business 333")
                .phone("131313")
                .address("city B street B")
                .build());

        assertNotNull(supplier);
        assertTrue(supplier.getId() > 0);
    }

    @Test
    @DisplayName("Repository should find an Supplier")
    void findByIdTest() {
        Supplier savedSupplier = repository.save(Supplier.builder()
                .businessName("Business 333")
                .phone("131313")
                .address("city B street B")
                .build());

        Optional<Supplier> Supplier = repository.findById(savedSupplier.getId());

        assertTrue(Supplier.isPresent());
        assertEquals(savedSupplier.getBusinessName(), Supplier.get().getBusinessName());
        assertEquals(savedSupplier.getAddress(), Supplier.get().getAddress());
        assertEquals(savedSupplier.getPhone(), Supplier.get().getPhone());
    }

    @Test
    @DisplayName("Repository should  find all Suppliers")
    void findAllTest() {
        Supplier savedSupplier = repository.save(Supplier.builder()
                .businessName("Business 333")
                .phone("131313")
                .address("city B street B")
                .build());
        Supplier savedSupplier2 = repository.save(Supplier.builder()
                .businessName("Business 3334")
                .phone("1313134")
                .address("city B street B5")
                .build());
        repository.save(savedSupplier);
        repository.save(savedSupplier2);

        List<Supplier> Suppliers = repository.findAll();

        assertFalse(Suppliers.isEmpty());
        assertTrue(Suppliers.size() == 2);

    }

    @Test
    @DisplayName("Repository should  find an Supplier by Email")
    void findByUserTest() {
        List<User> users = userRepository.findAll();
        User user = userRepository.findById(users.get(0).getId()).get();

        Supplier savedSupplier = repository.save(Supplier.builder()
                .businessName("Business 333")
                .phone("131313")
                .address("city B street B")
                .user(user)
                .build());

        Optional<Supplier> Supplier = repository.findByUser(user);

        assertTrue(Supplier.isPresent());
        assertEquals(savedSupplier.getBusinessName(), Supplier.get().getBusinessName());
        assertEquals(savedSupplier.getAddress(), Supplier.get().getAddress());
        assertEquals(savedSupplier.getPhone(), Supplier.get().getPhone());

    }

    @Test
    @DisplayName("Repository should update an Supplier")
    void updateTest() {
        Supplier savedSupplier = repository.save(Supplier.builder()
                .businessName("Business 333")
                .phone("131313")
                .address("city B street B")
                .build());

        Supplier Supplier = repository.findById(savedSupplier.getId()).get();

        Supplier.setBusinessName("updated");
        Supplier.setPhone("updated");
        Supplier.setAddress("updated");
        Supplier updatedSupplier = repository.save(Supplier);

        assertNotNull(updatedSupplier);
        assertEquals(updatedSupplier.getBusinessName(), "updated");
        assertEquals(updatedSupplier.getPhone(), "updated");
        assertEquals(updatedSupplier.getAddress(), "updated");

    }

    @Test
    @DisplayName("Repository should update an Supplier")
    void deleteTest() {
        Supplier savedSupplier = repository.save(Supplier.builder()
                .businessName("Business 333")
                .phone("131313")
                .address("city B street B")
                .build());

        repository.deleteById(savedSupplier.getId());
        Optional<Supplier> Supplier = repository.findById(savedSupplier.getId());

        assertTrue(Supplier.isEmpty());
    }
}
