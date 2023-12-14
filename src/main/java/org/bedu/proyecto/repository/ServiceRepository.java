package org.bedu.proyecto.repository;


import java.util.List;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<AppService,Long>{
    List<AppService> findAll();
    List<AppService> findAllBySuppliers(Supplier supplier);
}
