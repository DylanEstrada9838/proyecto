package org.bedu.proyecto.service;

import java.util.List;

import org.bedu.proyecto.dto.CreateUserDTO;
import org.bedu.proyecto.dto.UserDTO;
import org.bedu.proyecto.mapper.UserMapper;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper mapper;

    public List<UserDTO> findAll(){
        List<User> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }

    public UserDTO save(CreateUserDTO data){
        User entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }
}
