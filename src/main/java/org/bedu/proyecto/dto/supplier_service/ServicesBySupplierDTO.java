package org.bedu.proyecto.dto.supplier_service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicesBySupplierDTO {
    private String service;
    private Integer yearsExperience;
    private Integer countRating;
    private BigDecimal averageRating;
}
