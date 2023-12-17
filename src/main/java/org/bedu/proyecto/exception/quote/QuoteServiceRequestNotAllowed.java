package org.bedu.proyecto.exception.quote;

import org.bedu.proyecto.exception.RuntimeException;

public class QuoteServiceRequestNotAllowed extends RuntimeException{
    public QuoteServiceRequestNotAllowed (long requestId){
        super("ERR_BAD_REQUEST","Not Allowed to create Quote for ServiceRequest:"+requestId,requestId);
    }
}
