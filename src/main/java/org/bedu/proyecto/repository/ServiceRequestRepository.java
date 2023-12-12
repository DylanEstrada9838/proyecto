package org.bedu.proyecto.repository;


import java.util.List;

import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.ServiceRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRequestRepository extends CrudRepository<ServiceRequest,Long>{
    List<ServiceRequest> findAll();
    List<ServiceRequest> findAllByClient(Client client);
    
}
