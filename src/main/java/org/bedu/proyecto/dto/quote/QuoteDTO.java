package org.bedu.proyecto.dto.quote;

import java.math.BigDecimal;
import java.time.Instant;

import org.bedu.proyecto.model_enums.StatusQuote;

import lombok.Data;

@Data
public class QuoteDTO {
    private long id;
    private BigDecimal totalCost;
    private StatusQuote status;
    private long quoteRequestId;
    private Instant createdAt;
}
