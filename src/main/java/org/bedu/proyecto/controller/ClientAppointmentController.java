package org.bedu.proyecto.controller;

import org.bedu.proyecto.dto.appointment.AppointmentDTO;
import org.bedu.proyecto.dto.appointment.CreateAppointmentDTO;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quotes")
public class ClientAppointmentController {
    @Autowired
    private AppointmentService service;

    @PostMapping("{quoteId}/appointments")
    public AppointmentDTO save(@PathVariable long quoteId,@RequestBody CreateAppointmentDTO data) throws QuoteNotFound{
        return service.save(quoteId,data);
    }
}
