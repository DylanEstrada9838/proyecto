package org.bedu.proyecto.dto.supplier;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSupplierDTO {
    @Schema(description = "Nombre del proveedor  ",example = "Supplier 1")
    @Min(3)
    private String businessName;
    @Schema(description = "Numero de telefono",example = "0123456789")
    @Min(5)
    private String phone;
    @Schema(description = "Numero de telefono",example = "0123456789")
    private String phone2;
    @Min(5)
    @Schema(description = "Dirección del proveedor",example = "789 Oak Lane Hamletville, XY 12345")
    @Min(5)
    private String address;
    

}