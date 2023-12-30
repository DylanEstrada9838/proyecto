package org.bedu.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;


import org.bedu.proyecto.dto.user.CreateUserDTO;
import org.bedu.proyecto.dto.user.UpdateUserDTO;
import org.bedu.proyecto.dto.user.UserDTO;
import org.bedu.proyecto.exception.user.PasswordNotAllowed;
import org.bedu.proyecto.exception.user.UserEmailAlreadyCreated;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.mapper.UserMapper;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper mapper;

    public List<UserDTO> findAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public UserDTO findById(Long userId) throws UserNotFoundException {
        User user = Validation.userExist(repository, userId);
        return mapper.toDTO(user);
    }

    public UserDTO save(CreateUserDTO data) throws UserEmailAlreadyCreated {
        Optional<User> userOptional = repository.findByEmail(data.getEmail());

        if (userOptional.isPresent()) {
            throw new UserEmailAlreadyCreated(data.getEmail());
        }
        User entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    public void update(long userId, UpdateUserDTO data) throws UserNotFoundException, PasswordNotAllowed {
        User user = Validation.userExist(repository, userId);
        log.info("data {}", user.getPassword());
        log.info("data {}", data.getPassword());
        //Validation password is not the same
        if(Objects.equals(user.getPassword(), data.getPassword())){
            throw new PasswordNotAllowed(userId);
        }
        mapper.update(user, data);
        repository.save(user);
    }

    public void deleteById(Long userId) throws UserNotFoundException {
        User user = Validation.userExist(repository, userId);
        repository.delete(user);
    }

}
