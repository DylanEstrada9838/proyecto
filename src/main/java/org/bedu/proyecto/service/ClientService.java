package org.bedu.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.ClientDTO;
import org.bedu.proyecto.dto.CreateClientDTO;
import org.bedu.proyecto.dto.UpdateClientDTO;
import org.bedu.proyecto.exception.ClientNotFoundException;
import org.bedu.proyecto.exception.UserNotFoundException;
import org.bedu.proyecto.mapper.ClientMapper;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    ClientRepository repository;
    @Autowired
    ClientMapper mapper;
    @Autowired
    UserRepository userRepository;

    public List<ClientDTO> findAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public ClientDTO findById(Long clientId) throws ClientNotFoundException {
        Optional<Client> clientOptional = repository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new ClientNotFoundException(clientId);
        }
        return mapper.toDTO(clientOptional.get());
    }

    public ClientDTO save(CreateClientDTO data) throws UserNotFoundException{
        Optional<User> userOptional = userRepository.findById(data.getUserId());
        if(!userOptional.isPresent()){
            throw new UserNotFoundException(data.getUserId());
        }
        Client entity = mapper.toModel(data);
        entity.setUser(userOptional.get());
        repository.save(entity);

        return mapper.toDTO(entity);
    }

    public void update(long clientId, UpdateClientDTO data) throws ClientNotFoundException {
        Optional<Client> clientOptional = repository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new ClientNotFoundException(clientId);
        }
        Client client = clientOptional.get();
        mapper.update(client, data);
        repository.save(client);
    }

    public void delete(long clientId) throws ClientNotFoundException {
        Optional<Client> clientOptional = repository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new ClientNotFoundException(clientId);
        }
        repository.delete(clientOptional.get());
    }
}
