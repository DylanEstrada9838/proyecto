package org.bedu.proyecto.dto.servicerequest;

import org.bedu.proyecto.model_enums.Urgency;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class UpdateServiceRequestDTO {
     @Size(min = 5, max = 300)
    private String description;

    @Schema(description = "Direccion de domicilio donde se solicita el servicio", example = "Calle 5 de Mayo ")
    @Positive
    private Long addressId;

    @Schema(description = "Nivel de urgencia del servicio", example = "High")
    private Urgency urgency;
    
    @Schema(description = "Tipo de servicio que se realizara", example = "1")
    @Positive
    private Long serviceId;
}
