package org.bedu.proyecto.dto.address;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressDTO {
    @Size(min = 5)
    private String line1;
    @Size(min = 5)
    private String line2;
    @Size(min = 5)
    private String city;
    @Size(min = 1)
    private String state;
    @Size(min = 5)
    private String postalCode;
    @Size(min = 5)
    private String notes;
}
