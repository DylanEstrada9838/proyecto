package org.bedu.proyecto.dto.quote;

import org.bedu.proyecto.model_enums.StatusQuote;

import lombok.Data;

@Data
public class QuoteDTO {
    private long id;
    private long requestId;
    private long supplierId;
    private double totalCost;
    private StatusQuote status;
}
