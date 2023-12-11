package org.bedu.proyecto.controller;

import org.bedu.proyecto.dto.CreateReqServiceDTO;
import org.bedu.proyecto.dto.ReqServiceDTO;
import org.bedu.proyecto.exception.ClientNotFoundException;
import org.bedu.proyecto.exception.ServiceNotFoundException;
import org.bedu.proyecto.service.ReqServiceService;
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
public class ReqServiceController {
    @Autowired
    ReqServiceService service;

    @PostMapping("{clientId}/reqservices")
    @ResponseStatus(HttpStatus.CREATED)
    public ReqServiceDTO save(@PathVariable long clientId, @Valid @RequestBody CreateReqServiceDTO data) throws ClientNotFoundException,ServiceNotFoundException{
        log.info("Received controller CreateReqServiceDTO: {}", data);
        return service.save(clientId,data);
    }
}
