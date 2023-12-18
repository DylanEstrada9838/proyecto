package org.bedu.proyecto.dto.appointment;

import jakarta.validation.constraints.Future;
import lombok.Data;
import java.time.LocalDateTime;

import org.bedu.proyecto.model_enums.StatusAppointment;

@Data
public class CreateAppointmentDTO {
    private StatusAppointment status = StatusAppointment.PENDING;
    @Future
    private LocalDateTime startDate;
    @Future
    private LocalDateTime endDate;
    
}
