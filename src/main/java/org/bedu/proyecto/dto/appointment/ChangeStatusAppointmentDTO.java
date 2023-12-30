package org.bedu.proyecto.dto.appointment;

import org.bedu.proyecto.model_enums.StatusAppointment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeStatusAppointmentDTO {
    @NotNull
    private StatusAppointment status;
}
