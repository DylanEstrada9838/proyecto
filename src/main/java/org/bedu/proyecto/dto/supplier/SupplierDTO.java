package org.bedu.proyecto.dto.supplier;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    @Schema(description = "ID del proveedor",example = "1")
    private long id;

    @Schema(description = "Nombre de Proveedor",example = "0123456789")
    private String businessName;

    @Schema(description = "Numero de telefono",example = "0123456789")
    private String phone;

    @Schema(description = "Numero de telefono",example = "0123456789")
    private String phone2;

    @Schema(description = "Dirección del proveedor",example = "789 Oak Lane Hamletville, XY 12345")
    private String address;



    
}
