package org.bedu.proyecto.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class RatingKey {
    @Column(name = "supplier_service_id")
    private SupplierServiceKey supplierServiceKey;

    @Column(name = "client_id")
    private Long clientId;

    
}
