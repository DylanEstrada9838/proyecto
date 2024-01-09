package org.bedu.proyecto.exception.request;

import org.bedu.proyecto.exception.RuntimeException;

public class UpdateServiceRequestNotAllowed extends RuntimeException {

    public UpdateServiceRequestNotAllowed(long serviceRequestId) {
        super("ERR_BAD_REQUEST", "Update ServiceRequest with status different than OPEN is not allowed", serviceRequestId);
       
    }
    
    
}
