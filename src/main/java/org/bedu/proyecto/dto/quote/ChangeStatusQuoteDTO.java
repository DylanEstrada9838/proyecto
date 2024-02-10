package org.bedu.proyecto.dto.quote;

import org.bedu.proyecto.model_enums.StatusQuote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusQuoteDTO {
    private StatusQuote status;
}