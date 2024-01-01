package org.bedu.proyecto.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateRatingDTO {
    @Positive
    @NotNull
    private Long serviceId;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

}
