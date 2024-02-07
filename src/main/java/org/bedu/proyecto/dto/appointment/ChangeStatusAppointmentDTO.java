package org.bedu.proyecto.dto.appointment;
import org.bedu.proyecto.model_enums.StatusAppointment;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeStatusAppointmentDTO {
    @NotNull
    private StatusAppointment status;
}
