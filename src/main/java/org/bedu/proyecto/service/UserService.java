package org.bedu.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.user.CreateUserDTO;
import org.bedu.proyecto.dto.user.UpdateUserDTO;
import org.bedu.proyecto.dto.user.UserDTO;
import org.bedu.proyecto.exception.UserNotFoundException;
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

    public List<UserDTO> findAll() {
        List<User> data = repository.findAll();
        return mapper.toDTOs(data);
    }

    public UserDTO findById(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = repository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(userId);
        }
        return mapper.toDTO(optionalUser.get());
    }

    public UserDTO save(CreateUserDTO data) {
        User entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    public void update(long userId, UpdateUserDTO data) throws UserNotFoundException {
        Optional<User> result = repository.findById(userId);
        if (!result.isPresent()) {
            throw new UserNotFoundException(userId);
        }
        User user = result.get();
        mapper.update(user, data);

        repository.save(user);
    }

    public void deleteById(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = repository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(userId);
        }
        repository.delete(optionalUser.get());
    }

}
