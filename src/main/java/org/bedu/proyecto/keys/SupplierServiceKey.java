package org.bedu.proyecto.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SupplierServiceKey {

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name="service_id")
    private Long serviceId;

    public SupplierServiceKey(Long supplierId, Long serviceId) {
        this.supplierId = supplierId;
        this.serviceId = serviceId;
    }

    public SupplierServiceKey() {
    }

}
