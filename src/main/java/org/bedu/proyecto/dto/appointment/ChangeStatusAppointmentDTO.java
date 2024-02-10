package org.bedu.proyecto.dto.appointment;
import org.bedu.proyecto.model_enums.StatusAppointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusAppointmentDTO {
    @NotNull
    private StatusAppointment status;
}
