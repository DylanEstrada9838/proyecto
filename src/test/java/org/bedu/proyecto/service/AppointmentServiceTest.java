package org.bedu.proyecto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.bedu.proyecto.dto.appointment.AppointmentDTO;
import org.bedu.proyecto.dto.appointment.ChangeStatusAppointmentDTO;
import org.bedu.proyecto.dto.appointment.CreateAppointmentDTO;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyCompleted;
import org.bedu.proyecto.exception.appointment.AppointmentAlreadyExist;
import org.bedu.proyecto.exception.appointment.AppointmentCreationNotAllowed;
import org.bedu.proyecto.exception.appointment.AppointmentNotFound;
import org.bedu.proyecto.exception.appointment.CannotChangeStatus;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.model.Appointment;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model_enums.StatusAppointment;
import org.bedu.proyecto.model_enums.StatusQuote;
import org.bedu.proyecto.model_enums.StatusQuoteRequest;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;
import org.bedu.proyecto.repository.AppointmentRepository;
import org.bedu.proyecto.repository.QuoteRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AppointmentServiceTest {
    @Autowired
    AppointmentService service;
    @MockBean
    AppointmentRepository repository;
    @MockBean
    QuoteRepository quoteRepository;
    @MockBean
    ServiceRequestRepository serviceRequestRepository;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Service should save an Appointment")
    void saveTest() throws QuoteNotFound, AppointmentAlreadyExist, AppointmentCreationNotAllowed {
        CreateAppointmentDTO createDTO = CreateAppointmentDTO.builder()
                .startDate(LocalDateTime.of(2024, 12, 1, 12, 0, 0))
                .build();

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .createdAt(Instant.now())
                .description("casa")
                .id(99)
                .status(StatusRequest.ASSIGNED)
                .urgency(Urgency.HIGH)
                .build();
        QuoteRequest quoteRequest = QuoteRequest.builder()
                .id(99L)
                .status(StatusQuoteRequest.PENDING)
                .createdAt(Instant.now())
                .serviceRequest(serviceRequest)
                .build();

        Quote quote = Quote.builder()
                .id(99L)
                .createdAt(Instant.now())
                .status(StatusQuote.ACCEPTED)
                .totalCost(new BigDecimal(9999))
                .quoteRequest(quoteRequest)
                .build();

        Appointment appointment = Appointment.builder()
                .startDate(LocalDateTime.of(2024, 12, 1, 12, 0, 0))
                .status(StatusAppointment.SCHEDULED)
                .createdAt(Instant.now())
                .quote(quote)
                .build();
        when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quote));

        AppointmentDTO appointmentDTO = service.save(99L, createDTO);
        assertNotNull(appointmentDTO);
        assertEquals(appointment.getId(), appointmentDTO.getId());
        assertEquals(appointment.getStartDate(), appointmentDTO.getStartDate());
        assertEquals(appointment.getStatus(), appointmentDTO.getStatus());
        verify(quoteRepository, times(1)).save(quote);
        verify(serviceRequestRepository, times(1)).save(serviceRequest);

    }

//     @Test
//     @DisplayName("Service should find an Appointment given a Quote")
//     void findByQuoteTest() throws QuoteNotFound, AppointmentNotFound {
//         Appointment appointment = Appointment.builder()
//                 .id(99L)
//                 .startDate(LocalDateTime.of(2024, 12, 01, 12, 0, 0))
//                 .status(StatusAppointment.SCHEDULED)
//                 .createdAt(Instant.now())
//                 .updatedAt(Instant.now())
//                 .build();

//         Quote quote = Quote.builder()
//                 .id(99L)
//                 .createdAt(Instant.now())
//                 .status(StatusQuote.ACCEPTED)
//                 .totalCost(new BigDecimal(9999))
//                 .appointment(appointment)
//                 .build();

//         when(quoteRepository.findById(anyLong())).thenReturn(Optional.of(quote));
//         when(repository.findByQuote(quote)).thenReturn(appointment);

//         AppointmentDTO appointmentDTO = service.findByQuote(99L);
//         assertNotNull(appointmentDTO);
//         assertEquals(appointment.getId(), appointmentDTO.getId());
//         assertEquals(appointment.getStatus(), appointmentDTO.getStatus());
//         assertEquals(appointment.getStartDate(), appointmentDTO.getStartDate());
//         assertEquals(quote.getCreatedAt(), appointmentDTO.getCreatedAt());

//     }

    @Test
    @DisplayName("Service should update an Appointment")
    void updateTest() throws AppointmentNotFound, AppointmentAlreadyCompleted, CannotChangeStatus {
        ChangeStatusAppointmentDTO dto = ChangeStatusAppointmentDTO.builder()
                .status(StatusAppointment.COMPLETED)
                .build();

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .createdAt(Instant.now())
                .description("casa")
                .id(99)
                .status(StatusRequest.ASSIGNED)
                .urgency(Urgency.HIGH)
                .build();

        QuoteRequest quoteRequest = QuoteRequest.builder()
                .id(99L)
                .status(StatusQuoteRequest.PENDING)
                .createdAt(Instant.now())
                .serviceRequest(serviceRequest)
                .build();

        Quote quote = Quote.builder()
                .id(99L)
                .createdAt(Instant.now())
                .status(StatusQuote.ACCEPTED)
                .totalCost(new BigDecimal(9999))
                .quoteRequest(quoteRequest)
                .build();

        Appointment appointment = Appointment.builder()
                .id(99L)
                .startDate(LocalDateTime.of(2024, 12, 01, 12, 0, 0))
                .status(StatusAppointment.SCHEDULED)
                .createdAt(Instant.now())
                .quote(quote)
                .build();

        when(repository.findById(anyLong())).thenReturn(Optional.of(appointment));

        service.update(99L, dto);
        assertEquals(dto.getStatus(), appointment.getStatus());
        verify(repository, times(1)).save(appointment);
        verify(quoteRepository, times(1)).save(quote);
        verify(serviceRequestRepository, times(1)).save(serviceRequest);

    }
}
