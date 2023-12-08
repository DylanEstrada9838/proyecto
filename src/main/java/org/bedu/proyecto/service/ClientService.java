package org.bedu.proyecto.service;

import java.util.Optional;

import org.bedu.proyecto.dto.ClientDTO;
import org.bedu.proyecto.dto.CreateClientDTO;
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


    public ClientDTO save(CreateClientDTO data){
        long userId = data.getUserId();
        Optional< User> user = userRepository.findById(userId);
        User user1 = user.get();
        Client entity = mapper.toModel(data);
        entity.setUser(user1);
        repository.save(entity);
        
        return mapper.toDTO(entity);
    }
}
