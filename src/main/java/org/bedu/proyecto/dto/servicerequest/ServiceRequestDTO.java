package org.bedu.proyecto.dto.servicerequest;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import org.bedu.proyecto.model.StatusRequest;
import org.bedu.proyecto.model.Urgency;

import lombok.Data;

@Data
public class ServiceRequestDTO {
    @Schema(description = "", example = "")
    private long id;

    @Schema(description = "Direccion de domicilio de Proveedor", example = "Calle 5 de Mayo ")
    private String address;

    @Schema(description = "Descripcion de el servicio ", example = "Broken desk")
    private String description;

    @Schema(description = "Estatus de el servicio", example = "OPEN")
    private StatusRequest status;

    @Schema(description = "Nivel de urgencia del servicio", example = "HiGH")
    private Urgency urgency;

    @Schema(description = "Fecha de servicio", example = "date-time")
    private Instant createdAt ;
}
