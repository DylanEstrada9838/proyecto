package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bedu.proyecto.dto.quote_request.CreateQuoteRequestDTO;
import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.exception.quote_request.QuoteRequestAlreadyExist;
import org.bedu.proyecto.exception.request.RequestSameUserNotAllowed;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.service.QuoteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Tag(name="Endpoints de ServiceRequestClientController",description="en construccion **")
@RestController // Esta anotación indica que la clase es un controlador REST.
@RequestMapping("requests") // Esta anotación mapea las solicitudes HTTP a métodos de controlador específicos.

public class QuoteRequestClientController { // Define una clase pública llamada ServiceRequestController.
    @Autowired // Esta anotación permite la inyección automática del bean ServiceRequestService.
    QuoteRequestService service; // Define una variable de instancia para el servicio.

    @PostMapping("{serviceRequestId}/quoterequests")
    @ResponseStatus(HttpStatus.CREATED)
    public QuoteRequestDTO save(@PathVariable long serviceRequestId,@Valid @RequestBody CreateQuoteRequestDTO data) throws ServiceRequestNotFound,SupplierNotFoundException, RequestSameUserNotAllowed, ServiceNotAssignedException, QuoteRequestAlreadyExist{
        return service.save(serviceRequestId, data);
    }

    @Operation(summary="Define un método para encontrar todas las cotizaciones de por solicitud de servicio")
    @GetMapping("{serviceRequestId}/quoterequests") // Mapea las solicitudes GET a este método.
    @ResponseStatus(HttpStatus.OK) // En caso de éxito, devuelve un estado HTTP 200 (OK).
    public List<QuoteRequestDTO> findAllByServiceRequest(@PathVariable long serviceRequestId) throws ServiceRequestNotFound{
        return service.findAllByServiceRequest(serviceRequestId); // Llama al método findAllByServiceRequest del servicio y devuelve el resultado.
    }
}