package org.bedu.proyecto.exception;

public class ServiceAlreadyAssignedException extends RuntimeException {
    public ServiceAlreadyAssignedException(long serviceId) {
        super("ERR_DUPLICATE_ENTRY", "Service " + serviceId + " Already Assigned to Supplier", serviceId);
    }
}
