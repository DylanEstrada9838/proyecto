package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bedu.proyecto.dto.user.UpdateUserDTO;
import org.bedu.proyecto.dto.user.UserDTO;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.user.PasswordNotAllowed;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Tag(name="Endpoints de Usuarios",description="CRUD de Usuarios")
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired 
    private UserService service;

    @Operation(summary="Este método devuelve una lista de todos los usuarios.")
    @GetMapping 
    @ResponseStatus(HttpStatus.OK) 
    public List<UserDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary="Este método devuelve un usuario específico por su ID.")
    @GetMapping("{id}") 
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable Long id) throws UserNotFoundException, UnauthorizedAction {
        if (service.retrieveUserId() != id) {
            throw new UnauthorizedAction();
        } else {
            return service.findById(id);
        }
       
        
    }

    @Operation(summary="Este método elimina un usuario existente en la base de datos deacuerdo a el ID de el path.")
    @DeleteMapping("{id}") 
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void deleteById(@PathVariable Long id) throws UserNotFoundException, UnauthorizedAction {
        if (service.retrieveUserId() != id) {
            throw new UnauthorizedAction();
        } else {
            service.deleteById(id);
        }
        
    }

    @Operation(summary="Este método actualiza un usuario existente en la base de datos datos deacuerdo a el ID de el path.")
    @PutMapping("{id}") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @Valid @RequestBody UpdateUserDTO data) throws UserNotFoundException, PasswordNotAllowed, UnauthorizedAction {
        if (service.retrieveUserId() != id) {
            throw new UnauthorizedAction();
        } else {
            service.update(id,data);
        }
    }
}
