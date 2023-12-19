package org.bedu.proyecto.dto.quote_request;

import org.bedu.proyecto.model_enums.StatusQuoteRequest;

import lombok.Data;

@Data
public class CreateQuoteRequestDTO {
    private long supplierId;
    private StatusQuoteRequest status = StatusQuoteRequest.PENDING;
}
