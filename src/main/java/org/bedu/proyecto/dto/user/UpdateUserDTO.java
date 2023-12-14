package org.bedu.proyecto.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @Schema(description = "actualizacion de contraseña",example = "1234567")
    @Size(min=3,max=50)
    @NotNull
    private String password;
}
