package org.bedu.proyecto.dto.quote;

import org.bedu.proyecto.model_enums.StatusQuote;

import lombok.Data;
@Data
public class ChangeStatusQuoteDTO {
    private StatusQuote status;
}