package org.bedu.proyecto.exception;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(long supplierId) {
        super("ERR_DATA_NOT FOUND", "Supplier not found", supplierId);
    }
}
