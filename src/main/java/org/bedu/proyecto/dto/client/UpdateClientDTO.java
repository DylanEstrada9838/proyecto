package org.bedu.proyecto.dto.client;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateClientDTO {

    @Schema(description = "Numero de telefono", example = "0123456789")
    private String phone;

    @Schema(description = "Nombre del Cliente", example = "Pepe")
    private String name;

    @Schema(description = "Apellido del Cliente", example = "Pecas")
    private String lastName;
}
