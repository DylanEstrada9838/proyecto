package org.bedu.proyecto.service;


import java.util.Optional;

import org.bedu.proyecto.dto.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.ServiceRequestDTO;
import org.bedu.proyecto.exception.ClientNotFoundException;
import org.bedu.proyecto.exception.ServiceNotFoundException;
import org.bedu.proyecto.mapper.ServiceRequestMapper;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ServiceRequestDTO save(long clientId,CreateServiceRequestDTO data) throws ClientNotFoundException,ServiceNotFoundException{
       
        log.info("Received CreateReqServiceDTO: {}", data);
        Optional <Client> clientOptional = clientRepository.findById(clientId);
        Optional <AppService> serviceOptional = serviceRepository.findById(data.getServiceId());

        if(!clientOptional.isPresent()){
            throw new ClientNotFoundException(clientId);
        }
        if(!serviceOptional.isPresent()){
            throw new ServiceNotFoundException(data.getServiceId());
        }
        ServiceRequest entity= mapper.toModel(data);
        entity.setClient(clientOptional.get());
        entity.setService(serviceOptional.get());
        entity.setUrgency(data.getUrgency());
        repository.save(entity);
        return mapper.toDTO(entity);
    }
}
