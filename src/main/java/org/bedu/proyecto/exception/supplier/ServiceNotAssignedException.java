package org.bedu.proyecto.exception.supplier;

import org.bedu.proyecto.exception.RuntimeException;

public class ServiceNotAssignedException extends RuntimeException {
    public ServiceNotAssignedException(long serviceId) {
        super("ERR_BAD_REQUEST", "Service " + serviceId + " is not assigned to Supplier", serviceId);
    }
}
