package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.bedu.proyecto.dto.quote_request.CreateQuoteRequestDTO;
import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.UpdateServiceRequestDTO;
import org.bedu.proyecto.exception.quote_request.QuoteRequestAcceptedExist;
import org.bedu.proyecto.exception.quote_request.QuoteRequestAlreadyExist;
import org.bedu.proyecto.exception.request.RequestSameUserNotAllowed;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.exception.request.UpdateServiceRequestNotAllowed;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierServiceNotActive;
import org.bedu.proyecto.service.QuoteRequestService;
import org.bedu.proyecto.service.ServiceRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@Tag(name = "Endpoints de ServiceRequestClientController", description = "en construccion **")
@RestController // Esta anotación indica que la clase es un controlador REST.
@RequestMapping("servicerequests") // Esta anotación mapea las solicitudes HTTP a métodos de controlador
                           // específicos.

public class ServiceRequestController { // Define una clase pública llamada ServiceRequestController.
     // Esta anotación permite la inyección automática del bean ServiceRequestService.
    private ServiceRequestService service; // Define una variable de instancia para el servicio.

    private QuoteRequestService quoteRequestService;

    public ServiceRequestController(ServiceRequestService service, QuoteRequestService quoteRequestService) {
        this.service = service;
        this.quoteRequestService = quoteRequestService;
    }

    @GetMapping("{serviceRequestId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceRequestDTO findById(@PathVariable long serviceRequestId) throws ServiceRequestNotFound{
        return service.findById(serviceRequestId);
    }

    @PostMapping("{serviceRequestId}/quoterequests")
    @ResponseStatus(HttpStatus.CREATED)
    public QuoteRequestDTO addQuoteRequest(@PathVariable long serviceRequestId,@Valid @RequestBody CreateQuoteRequestDTO data) throws ServiceRequestNotFound,SupplierNotFoundException, RequestSameUserNotAllowed, ServiceNotAssignedException, QuoteRequestAlreadyExist, QuoteRequestAcceptedExist, SupplierServiceNotActive{
        return quoteRequestService.save(serviceRequestId, data);
    }

    @Operation(summary="Define un método para encontrar todas las cotizaciones de por solicitud de servicio")
    @GetMapping("{serviceRequestId}/quoterequests") // Mapea las solicitudes GET a este método.
    @ResponseStatus(HttpStatus.OK) // En caso de éxito, devuelve un estado HTTP 200 (OK).
    public List<QuoteRequestDTO> findAllQuoteRequestByServiceRequest(@PathVariable long serviceRequestId) throws ServiceRequestNotFound{
        return quoteRequestService.findAllByServiceRequest(serviceRequestId); // Llama al método findAllByServiceRequest del servicio y devuelve el resultado.
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(long serviceRequestId,UpdateServiceRequestDTO data) throws ServiceRequestNotFound, UpdateServiceRequestNotAllowed{
        service.update(serviceRequestId, data);
    }
}
