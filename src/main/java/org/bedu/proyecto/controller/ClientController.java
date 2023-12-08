package org.bedu.proyecto.controller;

import org.bedu.proyecto.dto.ClientDTO;
import org.bedu.proyecto.dto.CreateClientDTO;
import org.bedu.proyecto.service.ClientService;
import org.bedu.proyecto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    ClientService service;
    @Autowired
    UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO save(@Valid @RequestBody CreateClientDTO data){

        return service.save(data);
    }

}
