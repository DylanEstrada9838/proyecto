package org.bedu.proyecto.dto.client;


import org.bedu.proyecto.model_enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateClientDTO {

    

    @Schema(description = "Nombre del Cliente", example = "Jonh")
    private String name;

    @Schema(description = "Apellido del Cliente", example = "Doe")
    private String lastName;
    
    @Schema(description = "Numero de telefono", example = "0123456789")
    private String phone;

    @Schema(description = "Numero de telefono", example = "0123456789")
    private String phone_2;
    @Schema(description = "Edad del cliente", example = "30")
    private int age;
    @Schema(description = "Género del cliente", example = "M")
    private Gender gender;
}
