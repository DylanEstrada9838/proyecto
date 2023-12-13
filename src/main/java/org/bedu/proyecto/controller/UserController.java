package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name="Endpoints de Usuarios",description="CRUD de Usuarios")
@RestController // Esta anotación indica que esta clase es un controlador REST.
@RequestMapping("users") // Esta anotación mapea las solicitudes HTTP a "/users" a los métodos en esta clase.
public class UserController {
    @Autowired // Esta anotación permite la inyección automática del bean 'UserService'.
    private UserService service;

    @Operation(summary="Este método devuelve una lista de todos los usuarios.")
    @GetMapping // Mapea las solicitudes GET a este método.
    @ResponseStatus(HttpStatus.OK) // Si el método se ejecuta con éxito, devuelve un estado HTTP 200 (OK).
    public List<UserDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary="Este método devuelve un usuario específico por su ID.")
    @GetMapping("{id}") // Mapea las solicitudes GET con un ID de usuario a este método.
    @ResponseStatus(HttpStatus.OK) // Si el método se ejecuta con éxito, devuelve un estado HTTP 200 (OK).
    public UserDTO findById(@PathVariable Long id) throws UserNotFoundException {
        return service.findById(id);
    }

    @Operation(summary="Este método guarda un nuevo usuario en la base de datos.")
    @PostMapping // Mapea las solicitudes POST a este método.
    @ResponseStatus(HttpStatus.CREATED) // Si el método se ejecuta con éxito, devuelve un estado HTTP 201 (CREADO).
    public UserDTO save(@Valid @RequestBody CreateUserDTO data) {
        return service.save(data);
    }

    @Operation(summary="Este método elimina un usuario existente en la base de datos deacuerdo a el ID de el path.")
    @DeleteMapping("{id}") // Mapea las solicitudes DELETE con un ID de usuario a este método.
    @ResponseStatus(HttpStatus.NO_CONTENT) // Si el método se ejecuta con éxito, pero no va a devolver ningún contenido HTTP 204 (NO CONTENT).
    public void deleteById(@PathVariable Long id) throws UserNotFoundException {
        service.deleteById(id);
    }

    @Operation(summary="Este método actualiza un usuario existente en la base de datos datos deacuerdo a el ID de el path.")
    @PutMapping("{userId}") // Mapea las solicitudes PUT con un ID de usuario a este método.
    @ResponseStatus(HttpStatus.NO_CONTENT) // Si el método se ejecuta con éxito, pero no va a devolver ningún contenido HTTP 204 (NO CONTENT).
    public void update(@PathVariable long userId, @Valid @RequestBody UpdateUserDTO data) throws UserNotFoundException {
        service.update(userId, data);
    }
}
