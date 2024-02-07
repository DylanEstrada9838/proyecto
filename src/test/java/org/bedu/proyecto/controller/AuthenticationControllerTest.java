package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.bedu.proyecto.dto.appointment.ChangeStatusAppointmentDTO;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyCompleted;
import org.bedu.proyecto.exception.appointment.AppointmentNotFound;
import org.bedu.proyecto.exception.appointment.CannotChangeStatus;
import org.bedu.proyecto.model_enums.StatusAppointment;
import org.bedu.proyecto.service.AppointmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


