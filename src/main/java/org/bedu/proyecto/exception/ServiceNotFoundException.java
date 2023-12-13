package org.bedu.proyecto.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(long serviceId) {
        super("ERR_DATA_NOT FOUND", "Service does not exist", serviceId);
    }
}
