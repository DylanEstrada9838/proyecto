package org.bedu.proyecto.dto.quote;

import java.math.BigDecimal;

import org.bedu.proyecto.model_enums.StatusQuote;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CreateQuoteDTO {
    @Positive
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=5, fraction=2)
    private BigDecimal totalCost;
    @Builder.Default
    private StatusQuote status = StatusQuote.PENDING;
    
}
