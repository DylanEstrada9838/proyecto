package org.bedu.proyecto.controller;

import org.bedu.proyecto.dto.authentication.AuthenticationRequest;
import org.bedu.proyecto.dto.authentication.AuthenticationResponse;
import org.bedu.proyecto.dto.authentication.RegisterRequest;
import org.bedu.proyecto.exception.authentication.UserOrPasswordIncorrect;
import org.bedu.proyecto.exception.user.UserEmailNotFound;
import org.bedu.proyecto.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping("users")
public class AuthenticationController {

    @Autowired
    AuthenticationService service;

    @PostMapping("sign-in")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest request) throws UserEmailNotFound, UserOrPasswordIncorrect{
        return service.authenticate(request);
    }
    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest request){
        return service.register(request);
    }
}
