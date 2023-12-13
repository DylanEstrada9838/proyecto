package org.bedu.proyecto.dto.supplier;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSupplierDTO {
    @Schema(description = "Nombre del provedor", example = "Supplier 1")
    @NotBlank
    private String businessName;

    @Schema(description = "Telefono del provedor", example = "0123456789")
    @NotBlank(message = "Phone cannot be blank")
    @Min(5)
    private String phone;

    @Schema(description = "ID de usario", example = "1")
    private long userId;
}
