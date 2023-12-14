package org.bedu.proyecto.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateClientDTO {
    @Positive
    private long userId;

    @Schema(description = "Numero de telefono", example = "0123456789")
    @NotBlank
    @Min(5)
    private String phone;

    @Schema(description = "Nombre del Cliente", example = "John")
    @NotBlank
    @Size(min = 5,max = 50)
    private String name;

    @Schema(description = "Apellido del Cliente", example = "Doe")
    @NotBlank
    @Size(min = 5,max = 50)
    private String lastName;
    
}
