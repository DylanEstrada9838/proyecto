package org.bedu.proyecto.controller;

import java.util.List;

import org.bedu.proyecto.dto.ClientDTO;
import org.bedu.proyecto.dto.CreateClientDTO;
import org.bedu.proyecto.dto.UpdateClientDTO;
import org.bedu.proyecto.exception.ClientNotFoundException;
import org.bedu.proyecto.service.ClientService;
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
@RequestMapping("clients")
public class ClientController {
    @Autowired
    ClientService service;
    @Autowired
    UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO findById(@PathVariable long clientId) throws ClientNotFoundException {
        return service.findById(clientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO save(@Valid @RequestBody CreateClientDTO data) {

        return service.save(data);
    }

    @PutMapping("{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long clientId, @Valid @RequestBody UpdateClientDTO data)
            throws ClientNotFoundException {
        service.update(clientId, data);
    }

    @DeleteMapping("{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long clientId) throws ClientNotFoundException {
        service.delete(clientId);
    }

}
