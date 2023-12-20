package org.bedu.proyecto.dto.servicerequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;

import lombok.Data;

@Data
public class CreateServiceRequestDTO {
    @Schema(description = "Descripcion de el servicio ", example = "Broken desk")
    @Size(min = 5, max = 500)
    @NotBlank
    private String description;

    @Schema(description = "Direccion de domicilio donde se solicita el servicio", example = "Calle 5 de Mayo ")
    @Size(min = 5, max = 100)
    @NotBlank
    private String address;

    @Schema(description = "Nivel de urgencia del servicio", example = "HiGH")
    private Urgency urgency;
    
    @Schema(description = "Tipo de servicio que se realizara", example = "1")
    @Positive
    private Long serviceId;

    @Schema(description = "Estatus de la solicitud del servicio", example = "ASSIGNED")
    private StatusRequest status = StatusRequest.OPEN;
}
