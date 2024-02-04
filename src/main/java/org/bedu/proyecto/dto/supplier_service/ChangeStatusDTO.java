package org.bedu.proyecto.dto.supplier_service;

import org.bedu.proyecto.model_enums.StatusSupplierServiceJoin;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeStatusDTO {
    @NotNull
    private StatusSupplierServiceJoin status;
}
