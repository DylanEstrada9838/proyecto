package org.bedu.proyecto.dto.supplier;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSupplierDTO {
    
    @Schema(description = "Nombre del proveedor", example = "Supplier 1")
    @NotBlank
    private String businessName;

    @Schema(description = "Telefono del proveedor", example = "0123456789")
    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 5)
    private String phone;

    @Schema(description = "Telefono del proveedor", example = "0123456789")
    @Size(min = 5)
    private String phone2;

    @Schema(description = "Dirección del proveedor", example = "789 Oak Lane Hamletville, XY 12345")
    @Size(min = 5)
    @NotBlank
    private String address;
}
