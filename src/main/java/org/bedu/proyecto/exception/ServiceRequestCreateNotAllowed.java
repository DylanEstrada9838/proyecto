package org.bedu.proyecto.exception;




public class ServiceRequestCreateNotAllowed  extends RuntimeException{
    public ServiceRequestCreateNotAllowed(long serviceId) {
        super("ERR_NOT_ALLOWED", "A service request with status open is already sent to this Supplier ", serviceId);
    } 
}
