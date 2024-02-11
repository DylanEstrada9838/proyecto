package org.bedu.proyecto.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private long id;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String notes;
}
