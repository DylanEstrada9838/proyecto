package org.bedu.proyecto.dto.supplier_service;

import org.bedu.proyecto.model_enums.StatusSupplierServiceJoin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeStatusDTO {
    @NotNull
    private StatusSupplierServiceJoin status;
}
