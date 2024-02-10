package org.bedu.proyecto.dto.servicerequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceRequestDTO {
    @Schema(description = "Descripcion de el servicio ", example = "Broken desk")
    @Size(min = 5, max = 300)
    @NotBlank
    private String description;

    @Schema(description = "Direccion de domicilio donde se solicita el servicio", example = "Calle 5 de Mayo ")
    @Positive
    @NotNull
    private Long addressId;

    @Schema(description = "Nivel de urgencia del servicio", example = "High")
    @NotNull
    private Urgency urgency;
    
    @Schema(description = "Tipo de servicio que se realizara", example = "1")
    @Positive
    @NotNull
    private Long serviceId;

    @Schema(description = "Estatus de la solicitud del servicio", example = "ASSIGNED")
    @Builder.Default
    private StatusRequest status = StatusRequest.OPEN;

   

    
}
