package org.bedu.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findAll();
    Optional<Supplier> findByUser(User user);
}
