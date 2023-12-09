package org.bedu.proyecto.repository;


import java.util.List;
import org.bedu.proyecto.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<Service,Long>{
    List<Service> findAll();
}
