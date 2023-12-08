package org.bedu.proyecto.repository;

import java.util.List;

import org.bedu.proyecto.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long>{
    List <Client> findAll();
}
