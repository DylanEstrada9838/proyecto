package org.bedu.proyecto.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRatingDTO {
    @Positive
    @NotNull
    private Long serviceId;

    @NotNull
    @Min(1)
    @Max(5)
    private int clientRating;

}
