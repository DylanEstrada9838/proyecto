package org.bedu.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateClientDTO {
    @NotBlank
    private String phone;

}
