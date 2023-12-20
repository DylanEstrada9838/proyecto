package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.service.QuoteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Tag(name="Endpoints de ServiceRequestSupplierController",description="en construccion **")
@RestController // Esta anotación indica que la clase es un controlador REST.
@RequestMapping("suppliers") // Esta anotación mapea las solicitudes HTTP a métodos de controlador específicos.

public class QuoteRequestSupplierController { // Define una clase pública llamada ServiceRequestController.
    @Autowired // Esta anotación permite la inyección automática del bean ServiceRequestService.
    QuoteRequestService service; // Define una variable de instancia para el servicio.

    @Operation(summary="Define un método para encontrar todas las solicitudes de servicio por proveedor.")
    @GetMapping("{supplierId}/quoterequests") // Mapea las solicitudes GET a este método.
    @ResponseStatus(HttpStatus.OK) // En caso de éxito, devuelve un estado HTTP 200 (OK).
    public List<QuoteRequestDTO> findAllByClient(@PathVariable long supplierId) throws SupplierNotFoundException{
        return service.findAllBySupplier(supplierId); // Llama al método findAllByClient del servicio y devuelve el resultado.
    }
}