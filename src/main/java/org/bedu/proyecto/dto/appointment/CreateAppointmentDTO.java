package org.bedu.proyecto.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import org.bedu.proyecto.model_enums.StatusAppointment;

@Data
@Builder
public class CreateAppointmentDTO {
    @Builder.Default
    private StatusAppointment status = StatusAppointment.SCHEDULED;
    @Future
    @NotNull
    private LocalDateTime startDate;
}
