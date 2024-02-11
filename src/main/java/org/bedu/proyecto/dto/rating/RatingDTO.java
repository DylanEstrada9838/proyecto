package org.bedu.proyecto.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class RatingDTO {
    private Long clientId;
    private Long supplierId;
    private Long serviceId;
    private int clientRating;
}
