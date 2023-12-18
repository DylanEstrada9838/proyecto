package org.bedu.proyecto.dto.supplier;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSupplierDTO {
    @Schema(description = "Nombre del proveedor", example = "Supplier 1")
    @NotBlank
    private String businessName;

    @Schema(description = "Telefono del proveedor", example = "0123456789")
    @NotBlank(message = "Phone cannot be blank")
    @Min(5)
    private String phone;

    @Schema(description = "Telefono del proveedor", example = "0123456789")
    @Min(5)
    private String phone_2;

    @Schema(description = "Dirección del proveedor", example = "789 Oak Lane Hamletville, XY 12345")
    @Min(5)
    private String address;


    @Schema(description = "ID de usuario", example = "1")
    private long userId;
}
