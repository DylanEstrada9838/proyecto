package org.bedu.proyecto.service;


import java.util.Optional;

import org.bedu.proyecto.dto.CreateReqServiceDTO;
import org.bedu.proyecto.dto.ReqServiceDTO;
import org.bedu.proyecto.exception.ClientNotFoundException;
import org.bedu.proyecto.exception.ServiceNotFoundException;
import org.bedu.proyecto.mapper.ReqServiceMapper;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.ReqService;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.ReqServiceRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReqServiceService {
    @Autowired
    ReqServiceRepository repository;

    @Autowired
    ReqServiceMapper mapper;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public ReqServiceDTO save(long clientId,CreateReqServiceDTO data) throws ClientNotFoundException,ServiceNotFoundException{
       
        log.info("Received CreateReqServiceDTO: {}", data);
        Optional <Client> clientOptional = clientRepository.findById(clientId);
        Optional <AppService> serviceOptional = serviceRepository.findById(data.getServiceId());

        if(!clientOptional.isPresent()){
            throw new ClientNotFoundException(clientId);
        }
        if(!serviceOptional.isPresent()){
            throw new ServiceNotFoundException(data.getServiceId());
        }
        ReqService entity= mapper.toModel(data);
        entity.setClient(clientOptional.get());
        entity.setService(serviceOptional.get());
        entity.setUrgency(data.getUrgency());
        repository.save(entity);
        return mapper.toDTO(entity);
    }
}
