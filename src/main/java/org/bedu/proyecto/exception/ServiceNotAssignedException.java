package org.bedu.proyecto.exception;

public class ServiceNotAssignedException extends RuntimeException {
    public ServiceNotAssignedException(long serviceId) {
        super("ERR_DATA_NOT FOUND", "Service is not assigned to Supplier", serviceId);
    }
}
