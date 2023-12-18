package org.bedu.proyecto.dto.client;

import org.bedu.proyecto.model_enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClientDTO {

    private long id;

    @Schema(description = "Nombre del Cliente", example = "John")
    private String name;

    @Schema(description = "Apellido del Cliente", example = "Doe")
    private String lastName;
    
    @Schema(description = "Numero de telefono", example = "0123456789")
    private String phone;
  
    private String phone_2;
   
    private int age;

    private Gender gender;
}
