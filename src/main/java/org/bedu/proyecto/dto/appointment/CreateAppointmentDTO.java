package org.bedu.proyecto.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.bedu.proyecto.model_enums.StatusAppointment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentDTO {
    @Builder.Default
    private StatusAppointment status = StatusAppointment.SCHEDULED;
    @Future
    @NotNull
    private LocalDateTime startDate;
}
