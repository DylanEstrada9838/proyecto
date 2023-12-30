package org.bedu.proyecto.exception.request;

import org.bedu.proyecto.exception.RuntimeException;

public class ServiceRequestNotFound  extends RuntimeException{
    public ServiceRequestNotFound(long requestId) {
        super("ERR_DATA_NOT_FOUND", "Service Request Not Found", requestId);
    } 
}

