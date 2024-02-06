package org.bedu.proyecto.dto.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    private long id;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String notes;
}
