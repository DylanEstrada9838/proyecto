package org.bedu.proyecto.dto.service;
import java.math.BigDecimal;

import org.bedu.proyecto.model_enums.StatusSupplierServiceJoin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddServiceDTO {

    @Schema(description = "ID de tipo de servicio que se ofrece ", example = "1")
    @Positive
    @NotNull
    private Long serviceId;
    @Positive
    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    private int yearsExperience;
    private BigDecimal averageRating = null;
    private Integer countRating = 0;
    private StatusSupplierServiceJoin status = StatusSupplierServiceJoin.ACTIVE;

    public AddServiceDTO(@Positive @NotNull Long serviceId, @Positive @NotNull @Min(1) @Max(99) int yearsExperience) {
        this.serviceId = serviceId;
        this.yearsExperience = yearsExperience;
    }

    
}
