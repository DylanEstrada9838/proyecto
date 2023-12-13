package org.bedu.proyecto.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateClientDTO {
    private long userId;

    @Schema(description = "Numero de telefono", example = "0123456789")
    @NotBlank
    @Min(5)
    private String phone;

    @Schema(description = "Nombre del Cliente", example = "Pepe")
    @NotBlank
    private String name;

    @Schema(description = "Apellido del Cliente", example = "Pecas")
    @NotBlank
    private String lastName;
    
}
