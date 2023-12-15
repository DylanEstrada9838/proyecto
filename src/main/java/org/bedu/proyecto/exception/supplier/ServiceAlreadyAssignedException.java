package org.bedu.proyecto.exception.supplier;

import org.bedu.proyecto.exception.RuntimeException;

public class ServiceAlreadyAssignedException extends RuntimeException {
    public ServiceAlreadyAssignedException(long serviceId) {
        super("ERR_DUPLICATE_ENTRY", "Service " + serviceId + " is already assigned to Supplier", serviceId);
    }
}
