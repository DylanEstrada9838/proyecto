package org.bedu.proyecto.dto.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddServiceDTO {

    @Schema(description = "ID de tipo de servicio que se ofrece ", example = "1")
    @Positive
    private Long serviceId;
}
