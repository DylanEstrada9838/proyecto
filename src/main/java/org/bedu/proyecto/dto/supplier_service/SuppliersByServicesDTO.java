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
public class SuppliersByServicesDTO {
    private String supplier;
    private Integer yearsExperience;
    private Integer countRating;
    private BigDecimal averageRating;
}
