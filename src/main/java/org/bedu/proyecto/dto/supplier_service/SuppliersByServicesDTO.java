package org.bedu.proyecto.dto.supplier_service;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuppliersByServicesDTO {
    private String supplier;
    private Integer yearsExperience;
    private Integer countRating;
    private BigDecimal averageRating;
}
