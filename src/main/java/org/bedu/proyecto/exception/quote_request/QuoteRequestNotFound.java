package org.bedu.proyecto.exception.quote_request;

import org.bedu.proyecto.exception.RuntimeException;

public class QuoteRequestNotFound extends RuntimeException{
    public QuoteRequestNotFound (long quoteRequestId){
        super("ERR_DATA_NOT_FOUND", "Quote Request Not Found", quoteRequestId);
    }
}
