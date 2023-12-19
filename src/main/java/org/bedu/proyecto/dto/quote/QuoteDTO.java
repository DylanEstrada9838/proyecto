package org.bedu.proyecto.dto.quote;

import java.math.BigDecimal;

import org.bedu.proyecto.model_enums.StatusQuote;

import lombok.Data;

@Data
public class QuoteDTO {
    private long id;
    private long quoteRequestId;
    private BigDecimal totalCost;
    private StatusQuote status;
}
