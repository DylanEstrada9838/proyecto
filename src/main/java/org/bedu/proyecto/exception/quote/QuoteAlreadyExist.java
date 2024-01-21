package org.bedu.proyecto.exception.quote;

import org.bedu.proyecto.exception.RuntimeException;

public class QuoteAlreadyExist extends RuntimeException{
    public QuoteAlreadyExist (long quoteRequestId){
        super("ERR_DUPLICATE_ENTRY","Quote for QuoteRequest already exist",quoteRequestId);
    }
}
