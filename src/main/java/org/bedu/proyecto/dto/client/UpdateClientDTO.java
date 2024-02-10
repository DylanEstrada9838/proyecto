package org.bedu.proyecto.dto.client;

import org.bedu.proyecto.model_enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientDTO {

    @Schema(description = "Nombre del Cliente", example = "Jonh")
    @Size(min = 3,max = 50)
    private String name;

    @Schema(description = "Apellido del Cliente", example = "Doe")
    @Size(min = 3,max = 50)
    private String lastName;

    @Schema(description = "Numero de telefono", example = "0123456789")
    @Min(5)
    private String phone;

    @Schema(description = "Numero de telefono", example = "0123456789")
    @Min(5)
    private String phone2;

    @Schema(description = "Edad del cliente", example = "30")
    @Min(value=1)
    @Max(value=99)
    private Integer age;
    
    @Schema(description = "Género del cliente", example = "M")
    private Gender gender;
}
