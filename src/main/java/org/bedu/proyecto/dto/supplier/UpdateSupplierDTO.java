package org.bedu.proyecto.dto.supplier;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateSupplierDTO {

    @Schema(description = "Numero de telefono",example = "0123456789")
    private String phone;

    @Schema(description = "Nombre del proveedor  ",example = "Supplier 1")
    private String businessName;

}