package org.bedu.proyecto.dto.supplier;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SupplierDTO {

    @Schema(description = "ID del proveedor",example = "1")
    private long id;

    @Schema(description = "Numero de telefono",example = "0123456789")
    private String phone;

    @Schema(description = "Numero de telefono",example = "0123456789")
    private String businessName;
}
