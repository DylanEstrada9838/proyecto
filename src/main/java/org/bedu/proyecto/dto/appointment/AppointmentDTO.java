package org.bedu.proyecto.dto.appointment;

import org.bedu.proyecto.model_enums.StatusAppointment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Instant;

@Data
@Builder
public class AppointmentDTO {
    private long id;
    private StatusAppointment status;
    private long quoteId;
    private LocalDateTime startDate;
    private Instant createdAt;
}
