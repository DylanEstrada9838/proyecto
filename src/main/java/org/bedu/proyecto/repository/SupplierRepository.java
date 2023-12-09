package org.bedu.proyecto.repository;

import java.util.List;

import org.bedu.proyecto.model.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    List<Supplier> findAll();
}
