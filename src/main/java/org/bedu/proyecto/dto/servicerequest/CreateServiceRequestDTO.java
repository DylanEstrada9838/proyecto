package org.bedu.proyecto.dto.servicerequest;



import io.swagger.v3.oas.annotations.media.Schema;
import org.bedu.proyecto.model.StatusRequest;
import org.bedu.proyecto.model.Urgency;

import lombok.Data;

@Data
public class CreateServiceRequestDTO {

    @Schema(description = "Direccion de domicilio donde se solicita el servicio", example = "Calle 5 de Mayo ")
    private String address;

    @Schema(description = "Descripcion de el servicio ", example = "Broken desk")
    private String description;

    @Schema(description = "Nivel de urgencia del servicio", example = "HiGH")
    private Urgency urgency;

    @Schema(description = "Tipo de servicio que se realizara", example = "1")
    private long serviceId;

    @Schema(description = "ID de Proveedor que se solicita el servicio", example = "1")
    private long supplierId;

    @Schema(description = "Estatus de la solicitud del servicio", example = "ASSIGNED")
    private StatusRequest status = StatusRequest.OPEN;
}
