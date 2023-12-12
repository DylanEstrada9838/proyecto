package org.bedu.proyecto.dto.client;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateClientDTO {
    private long userId;
    @NotBlank
    @Min(5)
    private String phone;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    
}
