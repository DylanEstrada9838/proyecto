package org.bedu.proyecto.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {

    @Schema(description = "Correo del Usuario", example = "usuario@dominio")

    @Email
    @NotBlank
    @Size(min = 3, max = 50)
    private String email;

    @Schema(description = "Contraseña del usuario", example = "123456")
    @NotBlank
    @Size(min = 3, max = 50)
    private String password;

}
