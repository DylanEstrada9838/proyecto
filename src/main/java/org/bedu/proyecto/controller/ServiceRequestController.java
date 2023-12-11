package org.bedu.proyecto.controller;

import org.bedu.proyecto.dto.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.ServiceRequestDTO;
import org.bedu.proyecto.exception.ClientNotFoundException;
import org.bedu.proyecto.exception.ServiceNotFoundException;
import org.bedu.proyecto.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("clients")
public class ServiceRequestController {
    @Autowired
    ServiceRequestService service;

    @PostMapping("{clientId}/request")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceRequestDTO save(@PathVariable long clientId, @Valid @RequestBody CreateServiceRequestDTO data) throws ClientNotFoundException,ServiceNotFoundException{
        log.info("Received controller CreateReqServiceDTO: {}", data);
        return service.save(clientId,data);
    }
}
