package org.bedu.proyecto.controller;

import java.util.List;

import org.bedu.proyecto.dto.user.CreateUserDTO;
import org.bedu.proyecto.dto.user.UpdateUserDTO;
import org.bedu.proyecto.dto.user.UserDTO;
import org.bedu.proyecto.exception.UserNotFoundException;
import org.bedu.proyecto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable Long id) throws UserNotFoundException {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@Valid @RequestBody CreateUserDTO data) {
        return service.save(data);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws UserNotFoundException {
        service.deleteById(id);
    }

    @PutMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long userId, @Valid @RequestBody UpdateUserDTO data) throws UserNotFoundException {
        service.update(userId, data);
    }

}
