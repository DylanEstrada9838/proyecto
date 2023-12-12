package org.bedu.proyecto.service;


import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.servicerequest.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.exception.ClientNotFoundException;
import org.bedu.proyecto.exception.ServiceNotAssignedException;
import org.bedu.proyecto.exception.ServiceNotFoundException;
import org.bedu.proyecto.exception.SupplierNotFoundException;
import org.bedu.proyecto.mapper.ServiceRequestMapper;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceRequestService {
    @Autowired
    ServiceRequestRepository repository;

    @Autowired
    ServiceRequestMapper mapper;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Transactional
    public ServiceRequestDTO save(long clientId,CreateServiceRequestDTO data) throws ClientNotFoundException,ServiceNotFoundException,SupplierNotFoundException,ServiceNotAssignedException{
       
        log.info("Received CreateReqServiceDTO: {}", data);
        Optional <Client> clientOptional = clientRepository.findById(clientId);
        Optional <AppService> serviceOptional = serviceRepository.findById(data.getServiceId());
        Optional <Supplier> supplierOptional = supplierRepository.findById(data.getSupplierId());


        if(clientOptional.isEmpty()){
            throw new ClientNotFoundException(clientId);
        }
        if(serviceOptional.isEmpty()){
            throw new ServiceNotFoundException(data.getServiceId());
        }
        if(supplierOptional.isEmpty()){
            throw new SupplierNotFoundException(data.getSupplierId());
        }

        List<AppService> listServices = supplierOptional.get().getServices();

        if(!listServices.contains(serviceOptional.get())){
            throw new ServiceNotAssignedException(serviceOptional.get().getId());
        }
        ServiceRequest entity= mapper.toModel(data);
        entity.setClient(clientOptional.get());
        entity.setService(serviceOptional.get());
        entity.setSupplier(supplierOptional.get());
        entity.setUrgency(data.getUrgency());
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    public List<ServiceRequest> findAllByClient(long clientId){
        Optional<Client> client = clientRepository.findById(clientId);

        return repository.findAllByClient(client.get());
    }
}
