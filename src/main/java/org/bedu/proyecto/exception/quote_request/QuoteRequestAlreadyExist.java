package org.bedu.proyecto.exception.quote_request;

import org.bedu.proyecto.exception.RuntimeException;

public class QuoteRequestAlreadyExist extends RuntimeException{
    public QuoteRequestAlreadyExist(long serviceRequestId,long supplierId ){
        super("ERR_DUPLICATE_ENTRY", "QuoteRequest for Supplier "+supplierId+ " to ServiceRequest "+serviceRequestId+ " already exist", serviceRequestId);
    }
}
