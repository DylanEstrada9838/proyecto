package org.bedu.proyecto.controller;

import java.util.List;

import org.bedu.proyecto.dto.CreateUserDTO;
import org.bedu.proyecto.dto.UserDTO;
import org.bedu.proyecto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public UserDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@Valid @RequestBody CreateUserDTO data) {
        return service.save(data);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
             service.deleteById(id);
    }

}
