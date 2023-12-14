package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.dto.client.CreateClientDTO;
import org.bedu.proyecto.dto.client.UpdateClientDTO;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.user.UserNotFoundException;
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

@Tag(name="Endpoints de Clientes",description="CRUD de Clientes")
@RestController  // Indica que la clase es un controlador REST.
@RequestMapping("clients")  // Mapea las solicitudes web a este controlador.
public class ClientController {
    @Autowired  // Inyecta automáticamente la dependencia ClientService.
    ClientService service;

    @Autowired  // Inyecta automáticamente la dependencia UserService.
    UserService userService;

    @Operation(summary="Devuelve una lista de todos los clientes.")
    @GetMapping  // Maneja las solicitudes GET a la ruta base ("clients").
    @ResponseStatus(HttpStatus.OK)  // Devuelve un estado HTTP 200 (OK) si el método se ejecuta con éxito.
    public List<ClientDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary="Devuelve el cliente con el ID especificado.")
    @GetMapping("{clientId}")  // Maneja las solicitudes GET a "clients/{clientId}".
    @ResponseStatus(HttpStatus.OK)  // Devuelve un estado HTTP 200 (OK) si el método se ejecuta con éxito.
    public ClientDTO findById(@PathVariable long clientId) throws ClientNotFoundException {
        return service.findById(clientId);
    }

    @Operation(summary="Crea un nuevo cliente con los datos proporcionados.")
    @PostMapping  // Maneja las solicitudes POST a la ruta base ("clients").
    @ResponseStatus(HttpStatus.CREATED)  // Devuelve un estado HTTP 201 (CREATED) si el método se ejecuta con éxito.
    public ClientDTO save(@Valid @RequestBody CreateClientDTO data) throws UserNotFoundException{
        return service.save(data);
    }

    @Operation(summary="Actualiza el cliente con el ID especificado.")
    @PutMapping("{clientId}")  // Maneja las solicitudes PUT a "clients/{clientId}".
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Si el método se ejecuta con éxito, pero no va a devolver ningún contenido HTTP 204 (NO CONTENT).
    public void update(@PathVariable long clientId, @Valid @RequestBody UpdateClientDTO data)
            throws ClientNotFoundException {
        service.update(clientId, data);
    }

    @Operation(summary="Elimina el cliente con el ID especificado.")
    @DeleteMapping("{clientId}")  // Maneja las solicitudes DELETE a "clients/{clientId}".
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Si el método se ejecuta con éxito, pero no va a devolver ningún contenido HTTP 204 (NO CONTENT).
    public void delete(@PathVariable long clientId) throws ClientNotFoundException {
        service.delete(clientId);
    }
}
