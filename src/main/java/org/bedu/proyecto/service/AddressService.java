package org.bedu.proyecto.service;

import java.util.List;

import org.bedu.proyecto.dto.address.AddressDTO;
import org.bedu.proyecto.dto.address.CreateAddressDTO;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.mapper.AddressMapper;
import org.bedu.proyecto.model.Address;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.repository.AddressRepository;
import org.bedu.proyecto.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository repository;
    @Autowired
    AddressMapper mapper;
    @Autowired
    ClientRepository clientRepository;
    public AddressDTO save(long clientId,CreateAddressDTO data) throws ClientNotFoundException{
        Client client = Validation.clientExist(clientRepository, clientId);

        Address entity = mapper.toModel(data);
        entity.setClient(client);

        repository.save(entity);
        return mapper.toDTO(entity);
    }

    public List<AddressDTO> findAllByClient(long clientId) throws ClientNotFoundException{
        Client client = Validation.clientExist(clientRepository, clientId);
        return mapper.toDTOs(repository.findAllByClient(client));
    }
}
