package org.bedu.proyecto.dto.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveServiceDTO {

    @Schema(description = "ID de tipo de servicio que se ofrece ", example = "1")
    @Positive
    @NotNull
    private Long serviceId;
}
