package org.bedu.proyecto.repository;


import java.util.List;


import org.bedu.proyecto.model.ReqService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReqServiceRepository extends CrudRepository<ReqService,Long>{
    List<ReqService> findAll();
    
}
