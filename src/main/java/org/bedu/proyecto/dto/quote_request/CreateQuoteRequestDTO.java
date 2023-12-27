package org.bedu.proyecto.dto.quote_request;

import org.bedu.proyecto.model_enums.StatusQuoteRequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateQuoteRequestDTO {
    @Positive
    @NotNull
    private Long supplierId;
    private StatusQuoteRequest status = StatusQuoteRequest.PENDING;
}
