package org.bedu.proyecto.repository;


import java.util.List;

import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest,Long>{
    List<ServiceRequest> findAll();
    List<ServiceRequest> findAllByClient(Client client);
    List<ServiceRequest> findAllBySupplier(Supplier supplier);
    
}
