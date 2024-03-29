package org.bedu.proyecto.dto.quote_request;
import java.time.Instant;
import org.bedu.proyecto.model_enums.StatusQuoteRequest;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class QuoteRequestDTO {
    private long id;
    private StatusQuoteRequest status;
    private long serviceRequestId;
    private long supplierId;
    private Instant createdAt;

}
