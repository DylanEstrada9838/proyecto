package org.bedu.proyecto.exception.supplier;

import org.bedu.proyecto.exception.RuntimeException;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(long supplierId) {
        super("ERR_DATA_NOT_FOUND", "Supplier " + supplierId + " Not Found", supplierId);
    }
}
