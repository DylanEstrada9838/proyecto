package org.bedu.proyecto.dto.quote;

import org.bedu.proyecto.model_enums.StatusQuote;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ChangeStatusQuoteDTO {
    private StatusQuote status;
}