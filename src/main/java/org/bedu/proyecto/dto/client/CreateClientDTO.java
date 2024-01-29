package org.bedu.proyecto.dto.client;

import org.bedu.proyecto.model_enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateClientDTO {

    @Schema(description = "Nombre del Cliente", example = "John")
    @NotBlank
    @Size(min = 3,max = 50)
    private String name;

    @Schema(description = "Apellido del Cliente", example = "Doe")
    @NotBlank
    @Size(min = 3,max = 50)
    private String lastName;

    @Schema(description = "Numero de telefono", example = "0123456789")
    @NotBlank
    @Size(min = 5)
    private String phone;

    @Schema(description = "Numero de telefono", example = "0123456789")
    @Size(min = 5)
    private String phone_2;

    @Min(value=1)
    @Max(value=99)
    @NotNull
    private Integer age;

    @NotNull
    private Gender gender;
    
}
