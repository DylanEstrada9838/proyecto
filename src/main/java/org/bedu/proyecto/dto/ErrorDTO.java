package org.bedu.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorDTO {

    @Schema(description = "Numero de Código de error", example = "xxx")
    private String code;

    @Schema(description = "Mensaje de el error", example = "mensaje")
    private String message;

    @Schema(description = "Descripción del error", example = "descripcion del error")
    private Object details;
}
