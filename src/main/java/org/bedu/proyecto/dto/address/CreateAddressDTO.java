package org.bedu.proyecto.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressDTO {
    @NotBlank
    @Size(min = 5)
    private String line1;

    @Size(min = 5)
    private String line2;

    @NotBlank
    @Size(min = 5)
    private String city;

    @NotBlank
    @Size(min = 1)
    private String state;

    @NotBlank
    @Size(min = 5)
    private String postalCode;
    
    @Size(min = 5)
    private String notes;
}
