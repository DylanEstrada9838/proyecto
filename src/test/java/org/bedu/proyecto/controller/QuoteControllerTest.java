package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;

import org.bedu.proyecto.dto.appointment.AppointmentDTO;
import org.bedu.proyecto.dto.appointment.CreateAppointmentDTO;
import org.bedu.proyecto.dto.quote.ChangeStatusQuoteDTO;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyExist;
import org.bedu.proyecto.exception.appointment.AppointmentCreationNotAllowed;
import org.bedu.proyecto.exception.appointment.AppointmentNotFound;
import org.bedu.proyecto.exception.quote.CannotChangeQuoteStatus;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.model_enums.StatusAppointment;
import org.bedu.proyecto.model_enums.StatusQuote;
import org.bedu.proyecto.service.AppointmentService;
import org.bedu.proyecto.service.QuoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class QuoteControllerTest {
    @MockBean
    QuoteService service;

    @MockBean
    AppointmentService appointmentService;
    @Autowired
    QuoteController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should update a quote")
    void updateTest() throws QuoteNotFound, CannotChangeQuoteStatus {
        ChangeStatusQuoteDTO dto = ChangeStatusQuoteDTO.builder()
                .status(StatusQuote.ACCEPTED)
                .build();
        controller.update(99L, dto);

        verify(service, times(1)).update(99L, dto);
    }

    @Test
    @DisplayName("Controller should add an appointment")
    void addAppointmentTest() throws QuoteNotFound, AppointmentAlreadyExist, AppointmentCreationNotAllowed {
        CreateAppointmentDTO createDto = CreateAppointmentDTO.builder()
                .startDate(LocalDateTime.of(2024, 12, 1, 0, 0, 0))
                .build();
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(99L)
                .createdAt(Instant.now())
                .startDate(LocalDateTime.of(2024, 12, 1, 0, 0, 0))
                .status(StatusAppointment.SCHEDULED)
                .build();
        when(appointmentService.save(anyLong(), any(CreateAppointmentDTO.class))).thenReturn(dto);
        AppointmentDTO result = controller.addAppointment(99L, createDto);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getCreatedAt(), result.getCreatedAt());
        assertEquals(dto.getStartDate(), result.getStartDate());
        assertEquals(dto.getStatus(), result.getStatus());

    }

    @Test
    @DisplayName("Controller should find an appointment given a quote")
    void findAppointmentTest() throws QuoteNotFound, AppointmentNotFound {
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(99L)
                .createdAt(Instant.now())
                .startDate(LocalDateTime.of(2024, 12, 1, 0, 0, 0))
                .status(StatusAppointment.SCHEDULED)
                .build();
        when(appointmentService.findByQuote(anyLong())).thenReturn(dto);

        AppointmentDTO result = controller.findAppointment(99L);
        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getCreatedAt(), result.getCreatedAt());
        assertEquals(dto.getStartDate(), result.getStartDate());
        assertEquals(dto.getStatus(), result.getStatus());

    }

}
