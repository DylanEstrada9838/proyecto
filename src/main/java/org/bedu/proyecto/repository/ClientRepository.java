package org.bedu.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAll();
    Optional<Client> findByUser(User user);
}
