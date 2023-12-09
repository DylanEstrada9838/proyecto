package org.bedu.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSupplierDTO {
    @NotBlank
    private String phone;

}