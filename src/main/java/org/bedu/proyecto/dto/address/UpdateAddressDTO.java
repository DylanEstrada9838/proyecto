package org.bedu.proyecto.dto.address;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAddressDTO {
    @Size(min = 5)
    private String line_1;
    @Size(min = 5)
    private String line_2;
    @Size(min = 5)
    private String city;
    @Size(min = 1)
    private String state;
    @Size(min = 5)
    private String postalCode;
    @Size(min = 5)
    private String notes;
}
