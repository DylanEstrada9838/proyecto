package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bedu.proyecto.dto.servicerequest.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.exception.ServiceRequestCreateNotAllowed;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@Tag(name="Endpoints de ServiceRequestClientController",description="en construccion **")
@RestController // Esta anotación indica que la clase es un controlador REST.
@RequestMapping("clients") // Esta anotación mapea las solicitudes HTTP a métodos de controlador específicos.

public class ServiceRequestClientController { // Define una clase pública llamada ServiceRequestController.
    @Autowired // Esta anotación permite la inyección automática del bean ServiceRequestService.
    ServiceRequestService service; // Define una variable de instancia para el servicio.

    @Operation(summary="Crea una solicitud de Servicio a un Proveedor específico.")
    @PostMapping("{clientId}/requests") // Mapea las solicitudes POST a este método.
    @ResponseStatus(HttpStatus.CREATED) // En caso de éxito, devuelve un estado HTTP 201 (CREADO).
    public ServiceRequestDTO save(@PathVariable long clientId, @Valid @RequestBody CreateServiceRequestDTO data) throws ClientNotFoundException,ServiceNotFoundException,SupplierNotFoundException,ServiceNotAssignedException,ServiceRequestCreateNotAllowed{ // Define un método para guardar una solicitud de servicio.

        return service.save(clientId,data); // Llama al método save del servicio y devuelve el resultado.
    }

    @Operation(summary="Define un método para encontrar todas las solicitudes de servicio por cliente.")
    @GetMapping("{clientId}/requests") // Mapea las solicitudes GET a este método.
    @ResponseStatus(HttpStatus.OK) // En caso de éxito, devuelve un estado HTTP 200 (OK).
    public List<ServiceRequestDTO> findAllByClient(@PathVariable long clientId) throws ClientNotFoundException{
        return service.findAllByClient(clientId); // Llama al método findAllByClient del servicio y devuelve el resultado.
    }
}
