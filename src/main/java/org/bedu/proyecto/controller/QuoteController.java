package org.bedu.proyecto.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bedu.proyecto.dto.appointment.AppointmentDTO;
import org.bedu.proyecto.dto.appointment.CreateAppointmentDTO;
import org.bedu.proyecto.dto.quote.ChangeStatusQuoteDTO;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyExist;
import org.bedu.proyecto.exception.appointment.AppointmentCreationNotAllowed;
import org.bedu.proyecto.exception.appointment.AppointmentNotFound;
import org.bedu.proyecto.exception.quote.CannotChangeQuoteStatus;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.service.AppointmentService;
import org.bedu.proyecto.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Endpoints de QuoteSupplierController",description="en construccion **")
@RestController // Esta anotación indica que la clase es un controlador REST.
@RequestMapping("quote") // Esta anotación mapea las solicitudes HTTP a métodos de controlador específicos.

public class QuoteController {
    @Autowired // Esta anotación permite la inyección automática del bean 
    QuoteService service; // Define una variable de instancia para el servicio.
    @Autowired
    AppointmentService appointmentService;

    @PutMapping("{quoteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long quoteId,@RequestBody ChangeStatusQuoteDTO data) throws QuoteNotFound, CannotChangeQuoteStatus{
        service.update(quoteId, data);
    }

    @Operation(summary="Crea una cotización a un QuoteRequest")
    @PostMapping("{quoteId}/appointment") 
    @ResponseStatus(HttpStatus.OK) // En caso de éxito, devuelve un estado HTTP 200 (OK).
    public AppointmentDTO addAppointment(@PathVariable long quoteId,@Valid @RequestBody CreateAppointmentDTO dto) throws QuoteNotFound, AppointmentAlreadyExist, AppointmentCreationNotAllowed{
        return appointmentService.save(quoteId,dto); 
    }
    @GetMapping("{quoteId}/appointment")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDTO findAppointment(@PathVariable long quoteId) throws QuoteNotFound, AppointmentNotFound{
        return appointmentService.findByQuote(quoteId);
    }

}

