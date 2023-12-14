package org.bedu.proyecto.repository;

import java.util.List;

import org.bedu.proyecto.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAll();
}
