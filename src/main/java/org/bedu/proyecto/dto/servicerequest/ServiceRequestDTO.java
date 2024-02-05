package org.bedu.proyecto.dto.servicerequest;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceRequestDTO {
    @Schema(description = "", example = "")
    private long id;

    @Schema(description = "Descripcion de el servicio ", example = "Broken desk")
    private String description;

    @Schema(description = "Direccion de domicilio donde se solicita el servicio", example = "Calle 5 de Mayo ")
    private Long addressId;

    @Schema(description = "Estatus de la solicitud del servicio", example = "OPEN")
    private StatusRequest status;

    @Schema(description = "Nivel de urgencia del servicio", example = "HiGH")
    private Urgency urgency;

    @Schema(description = "Tipo de Servicio que se realizara", example = "1")
    private Long serviceId;

    @Schema(description = "ID de Cliente que se solicita el servicio", example = "1")
    private Long clientId;

    @Schema(description = "Fecha de servicio", example = "date-time")
    private Instant createdAt;
}
