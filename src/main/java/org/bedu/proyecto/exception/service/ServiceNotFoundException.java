package org.bedu.proyecto.exception.service;

import org.bedu.proyecto.exception.RuntimeException;

public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(long serviceId) {
        super("ERR_DATA_NOT_FOUND", "Service " + serviceId + " Not Found", serviceId);
    }
}
