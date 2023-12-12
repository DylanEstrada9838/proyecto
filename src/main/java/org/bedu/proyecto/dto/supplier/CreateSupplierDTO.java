package org.bedu.proyecto.dto.supplier;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSupplierDTO {
    @NotBlank
    private String businessName;
    @NotBlank(message = "Phone cannot be blank")
    @Min(5)
    private String phone;
    private long userId;
}
