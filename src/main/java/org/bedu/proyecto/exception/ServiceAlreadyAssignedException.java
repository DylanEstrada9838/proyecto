package org.bedu.proyecto.exception;

public class ServiceAlreadyAssignedException extends RuntimeException {
    public ServiceAlreadyAssignedException(long serviceId) {
        super("ERR_DATA_NOT FOUND", "Service is already assigned to Supplier", serviceId);
    }
}
