package org.bedu.proyecto.dto.supplier_service;

import org.bedu.proyecto.model_enums.StatusSupplierServiceJoin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDTO {
    @NotNull
    private StatusSupplierServiceJoin status;
}
