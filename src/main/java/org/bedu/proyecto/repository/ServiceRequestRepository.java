package org.bedu.proyecto.repository;


import java.util.List;

import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest,Long>{
    List<ServiceRequest> findAll();
    List<ServiceRequest> findAllByClient(Client client);
    @Query("SELECT sr FROM ServiceRequest sr WHERE sr.address.id = :addressId AND (sr.status = 'SCHEDULED' OR sr.status = 'IN_PROCESS' OR sr.status = 'ASSIGNED')")
    List<ServiceRequest> findAllByAddress(long addressId);
    
}
