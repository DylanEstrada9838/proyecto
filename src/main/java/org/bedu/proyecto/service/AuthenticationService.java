package org.bedu.proyecto.service;

import org.bedu.proyecto.dto.authentication.AuthenticationRequest;
import org.bedu.proyecto.dto.authentication.AuthenticationResponse;
import org.bedu.proyecto.dto.authentication.RegisterRequest;
import org.bedu.proyecto.exception.authentication.UserOrPasswordIncorrect;
import org.bedu.proyecto.exception.user.UserEmailAlreadyCreated;
import org.bedu.proyecto.exception.user.UserEmailNotFound;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Role;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    
    private UserRepository repository;

    
    private PasswordEncoder passwordEncoder;

    
    private JwtService jwtService;

    
    private AuthenticationManager manager;
    
    
    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager manager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.manager = manager;
    }

    public AuthenticationResponse register(RegisterRequest request) throws UserEmailAlreadyCreated {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
                     throw new UserEmailAlreadyCreated(request.getEmail());
        }
        var user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .build();
        log.info( "data {}", user);
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
      }
      
      public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserEmailNotFound, UserOrPasswordIncorrect{
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new UserEmailNotFound(request.getEmail()));
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            // Handle BadCredentialsException explicitly
            throw new UserOrPasswordIncorrect(e);
        }
    }
    
}
