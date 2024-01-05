package org.bedu.proyecto.dto.supplier_service;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SuppliersByServicesDTO {
    private String supplier;
    private Integer yearsExperience;
    private Integer countRating;
    private BigDecimal averageRating;
}
