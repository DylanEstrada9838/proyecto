package org.bedu.proyecto.dto.quote;

import org.bedu.proyecto.model_enums.StatusQuote;

import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
public class CreateQuoteDTO {
    @Positive
    private Double totalCost;
    private StatusQuote status = StatusQuote.PENDING;
    
}
