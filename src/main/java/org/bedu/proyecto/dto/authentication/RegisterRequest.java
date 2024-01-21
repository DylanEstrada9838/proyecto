package org.bedu.proyecto.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email
    @NotBlank
    @Size(min = 3, max = 50)
    private String email;

    @Schema(description = "Contraseña del usuario", example = "123456")
    @NotBlank
    @Size(min = 3, max = 50)
    private String password;
}
