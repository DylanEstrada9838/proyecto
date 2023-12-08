package org.bedu.proyecto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateClientDTO {
    @NotBlank
    @Min(5)
    private String phone;
    private long userId;
}
