package org.bedu.proyecto.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Endpoints de QuoteSupplierController",description="en construccion **")
@RestController // Esta anotación indica que la clase es un controlador REST.
@RequestMapping("suppliers") // Esta anotación mapea las solicitudes HTTP a métodos de controlador específicos.

public class QuoteSupplierController { // Define una clase pública llamada ServiceRequestController.
    @Autowired // Esta anotación permite la inyección automática del bean ServiceRequestService.
    QuoteService service; // Define una variable de instancia para el servicio.

    @Operation(summary="Crea una cotización a un ServiceRequest")
    @PostMapping("{supplierId}/requests/{requestId}") // Mapea las solicitudes GET a este método.
    @ResponseStatus(HttpStatus.OK) // En caso de éxito, devuelve un estado HTTP 200 (OK).
    public QuoteDTO save(@PathVariable long supplierId,@PathVariable long requestId,@Valid @RequestBody CreateQuoteDTO dto){
        return service.save(supplierId,requestId,dto); // Llama al método findAllByClient del servicio y devuelve el resultado.
    }
}

