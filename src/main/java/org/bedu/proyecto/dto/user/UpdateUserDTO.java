package org.bedu.proyecto.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @Schema(description = "actualizacion de contraseaña",example = "1234567")
    private String password;
}
