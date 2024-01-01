package org.bedu.proyecto.dto.rating;

import lombok.Data;

@Data
public class RatingDTO {
    private Long clientId;
    private Long supplierId;
    private Long serviceId;
    private int rating;
}
