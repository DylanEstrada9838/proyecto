package org.bedu.proyecto.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(long serviceId) {
        super("ERR_DATA_NOT FOUND", "Service does not exist", serviceId);
    }
}
