package org.bedu.proyecto.dto.appointment;

import org.bedu.proyecto.model_enums.StatusAppointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private long id;
    private StatusAppointment status;
    private long quoteId;
    private LocalDateTime startDate;
    private Instant createdAt;
}
