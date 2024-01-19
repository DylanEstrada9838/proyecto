package org.bedu.proyecto.service;

import java.util.List;

import org.bedu.proyecto.dto.address.AddressDTO;
import org.bedu.proyecto.dto.address.CreateAddressDTO;
import org.bedu.proyecto.dto.address.UpdateAddressDTO;
import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.address.UpdateOrDeleteNotAllowed;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.mapper.AddressMapper;
import org.bedu.proyecto.model.Address;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.repository.AddressRepository;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressService {
    @Autowired
    AddressRepository repository;
    @Autowired
    AddressMapper mapper;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ServiceRequestRepository serviceRequestRepository;

    public AddressDTO save(long clientId,CreateAddressDTO data) throws ClientNotFoundException{
        Client client = Validation.clientExist(clientRepository, clientId);

        Address entity = mapper.toModel(data);
        entity.setClient(client);

        repository.save(entity);
        return mapper.toDTO(entity);
    }

    public void update(long addressId,UpdateAddressDTO data) throws AddressNotFound, UpdateOrDeleteNotAllowed{
        Address address = Validation.addressExist(repository, addressId);
        log.info("data {}", data);
        //Validation Address is not currently used by ServiceRequest
        List<ServiceRequest> serviceRequests = serviceRequestRepository.findAllByAddress(addressId);
        for (ServiceRequest serviceRequest : serviceRequests){
            if (serviceRequest.getStatus() == StatusRequest.IN_PROCESS || serviceRequest.getStatus() == StatusRequest.ASSIGNED || serviceRequest.getStatus() == StatusRequest.SCHEDULED ){
                throw new UpdateOrDeleteNotAllowed(addressId);
            }
        }
        mapper.update(address, data);
        repository.save(address);
    }

    public void delete(long addressId) throws AddressNotFound, UpdateOrDeleteNotAllowed{
        Address address = Validation.addressExist(repository, addressId);
        //Validation Address is not currently used by ServiceRequest
        List<ServiceRequest> serviceRequests = serviceRequestRepository.findAllByAddress(addressId);
        for (ServiceRequest serviceRequest : serviceRequests){
            if (serviceRequest.getStatus() == StatusRequest.IN_PROCESS || serviceRequest.getStatus() == StatusRequest.ASSIGNED || serviceRequest.getStatus() == StatusRequest.SCHEDULED ){
                throw new UpdateOrDeleteNotAllowed(addressId);
            }
        }
        repository.delete(address);
    }

    public List<AddressDTO> findAllByClient(long clientId) throws ClientNotFoundException{
        Client client = Validation.clientExist(clientRepository, clientId);
        return mapper.toDTOs(repository.findAllByClient(client));
    }
}
