package org.bedu.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.dto.client.CreateClientDTO;
import org.bedu.proyecto.dto.client.UpdateClientDTO;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.client.ClientUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
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
        Client client = Validation.clientExist(repository, clientId);
        return mapper.toDTO(client);
    }

    public ClientDTO save(CreateClientDTO data) throws UserNotFoundException, ClientUserAlreadyExist {
        User user = Validation.userExist(userRepository, data.getUserId());

        Optional<Client> clientOptional = repository.findByUser(user);
        if (clientOptional.isPresent()) {
            throw new ClientUserAlreadyExist(data.getUserId());
        }
        Client entity = mapper.toModel(data);
        entity.setUser(user);
        repository.save(entity);

        return mapper.toDTO(entity);
    }

    public void update(long clientId, UpdateClientDTO data) throws ClientNotFoundException {
        Client client = Validation.clientExist(repository, clientId);
        mapper.update(client, data);
        repository.save(client);
    }

    public void delete(long clientId) throws ClientNotFoundException {
        Client client = Validation.clientExist(repository, clientId);
         repository.delete(client);
    }
}
