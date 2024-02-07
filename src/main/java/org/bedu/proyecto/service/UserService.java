package org.bedu.proyecto.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.bedu.proyecto.dto.user.UpdateUserDTO;
import org.bedu.proyecto.dto.user.UserDTO;
import org.bedu.proyecto.exception.user.PasswordNotAllowed;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.mapper.UserMapper;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository repository;
    
    private UserMapper mapper;
    
    private PasswordEncoder encoder;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    public List<UserDTO> findAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public UserDTO findById(Long userId) throws UserNotFoundException {
        return mapper.toDTO(Validation.userExists(repository, userId));
    }

    public void update(long userId, UpdateUserDTO data) throws UserNotFoundException, PasswordNotAllowed {
        User user = Validation.userExists(repository, userId);
        //Validation password is not the same
        if(Objects.equals(user.getPassword(), data.getPassword())){
            throw new PasswordNotAllowed(userId);
        }
        //Hashing password
        String hashedPassword = encoder.encode(data.getPassword());
        data.setPassword(hashedPassword);

        mapper.update(user, data);
        repository.save(user);
    }

    public void deleteById(Long userId) throws UserNotFoundException {
        repository.delete(Validation.userExists(repository, userId));
    }

    public Long retrieveUserId() throws UserNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = repository.findByEmail(authentication.getName());
        if (user.isPresent()){
           return user.get().getId(); 
        } else {
            throw new UserNotFoundException(0);
        }
        
    }

}
