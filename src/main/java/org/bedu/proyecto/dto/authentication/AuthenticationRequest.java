package org.bedu.proyecto.dto.authentication;

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
public class AuthenticationRequest {
    @Email
    @NotBlank
    @Size(min = 3, max = 50)
    private String email;
    @NotBlank
    @Size(min = 3, max = 50)
    private String password;
}
