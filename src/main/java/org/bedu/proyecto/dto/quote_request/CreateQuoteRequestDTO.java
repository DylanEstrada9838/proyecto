package org.bedu.proyecto.dto.quote_request;


import org.bedu.proyecto.model_enums.StatusQuoteRequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuoteRequestDTO {
    @Positive
    @NotNull
    private Long supplierId;
    @Builder.Default 
    private StatusQuoteRequest status = StatusQuoteRequest.PENDING;
}
