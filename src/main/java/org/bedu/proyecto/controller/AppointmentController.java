package org.bedu.proyecto.controller;

import org.bedu.proyecto.dto.appointment.ChangeStatusAppointmentDTO;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyCompleted;
import org.bedu.proyecto.exception.appointment.AppointmentNotFound;
import org.bedu.proyecto.exception.appointment.CannotChangeStatus;
import org.bedu.proyecto.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("appointment")
public class AppointmentController {

    @Autowired
    AppointmentService service;

    @PutMapping("{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long appointmentId, @Valid @RequestBody ChangeStatusAppointmentDTO data)
            throws AppointmentNotFound, AppointmentAlreadyCompleted, CannotChangeStatus {
        service.update(appointmentId, data);
    }

}
