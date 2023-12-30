package org.bedu.proyecto.exception.request;

import org.bedu.proyecto.exception.RuntimeException;

public class ServiceRequestCreateNotAllowed  extends RuntimeException{
    public ServiceRequestCreateNotAllowed(long serviceId) {
        super("ERR_BAD_REQUEST", "A ServiceRequest for the same service with status different that COMPLETED is already created ", serviceId);
    } 
}
