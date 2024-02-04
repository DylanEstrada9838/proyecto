package org.bedu.proyecto.dto.supplier_service;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServicesBySupplierDTO {
    private String service;
    private Integer yearsExperience;
    private Integer countRating;
    private BigDecimal averageRating;
}
