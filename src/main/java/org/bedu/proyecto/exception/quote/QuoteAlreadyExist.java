package org.bedu.proyecto.exception.quote;

import org.bedu.proyecto.exception.RuntimeException;

public class QuoteAlreadyExist extends RuntimeException{
    public QuoteAlreadyExist (long quoteRequestId){
        super("ERR_BAD_REQUEST","Quote for QuoteRequest already exist",quoteRequestId);
    }
}
