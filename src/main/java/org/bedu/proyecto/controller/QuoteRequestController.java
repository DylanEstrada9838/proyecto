package org.bedu.proyecto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.exception.quote.QuoteAlreadyExist;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.exception.quote_request.QuoteRequestNotFound;
import org.bedu.proyecto.service.QuoteRequestService;
import org.bedu.proyecto.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Tag(name="Endpoints de ServiceRequestSupplierController",description="en construccion **")
@RestController // Esta anotación indica que la clase es un controlador REST.
@RequestMapping("quoterequests") // Esta anotación mapea las solicitudes HTTP a métodos de controlador específicos.

public class QuoteRequestController { // Define una clase pública llamada ServiceRequestController.
    @Autowired // Esta anotación permite la inyección automática del bean ServiceRequestService.
    QuoteRequestService service; // Define una variable de instancia para el servicio.
    @Autowired
    QuoteService quoteService;
    

    @Operation(summary="Crea una cotización a un ServiceRequest")
    @PostMapping("{quoteRequestId}/quote") 
    @ResponseStatus(HttpStatus.CREATED) // En caso de éxito, devuelve un estado HTTP 200 (OK).
    public QuoteDTO addQuote(@PathVariable long quoteRequestId,@Valid @RequestBody CreateQuoteDTO dto) throws QuoteAlreadyExist, QuoteRequestNotFound{
        return quoteService.save(quoteRequestId,dto); 
    }

    @GetMapping("{quoteRequestId}/quote")
    @ResponseStatus(HttpStatus.OK)
    public QuoteDTO findQuote(@PathVariable long quoteRequestId) throws QuoteRequestNotFound, QuoteNotFound{
        return quoteService.findByQuoteRequest(quoteRequestId);
    }
}