package org.bedu.proyecto.dto.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RemoveServiceDTO {

    @Schema(description = "ID de tipo de servicio que se ofrece ", example = "1")
    private long serviceId;
}
