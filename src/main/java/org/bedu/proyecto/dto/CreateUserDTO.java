package org.bedu.proyecto.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 3, max = 50)
    private String password;

}
