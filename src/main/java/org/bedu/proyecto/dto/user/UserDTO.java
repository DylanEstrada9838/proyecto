package org.bedu.proyecto.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {

    @Schema(description = "ID del Usuario",example = "1")
    private long id;

    @Schema(description = "Email del Usuario",example = "usuario@dominio")
    private String email;

    @Schema(description = "Contraseña del Usuario",example = "1234567")
    private String password;
}
