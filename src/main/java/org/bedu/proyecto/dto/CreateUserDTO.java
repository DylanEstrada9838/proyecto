package org.bedu.proyecto.dto;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 50)
    @NotBlank
    private String lastname;

    @Size(min = 3, max = 50)
    @NotBlank
    @Email
    private String email;

    
    @NotBlank
    @Size(min = 3, max = 50)
    private String phone;

    @Past
    private Date date;
}
