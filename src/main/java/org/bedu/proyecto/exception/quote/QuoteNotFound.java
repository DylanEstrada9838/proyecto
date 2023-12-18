package org.bedu.proyecto.exception.quote;

import org.bedu.proyecto.exception.RuntimeException;

public class QuoteNotFound extends RuntimeException{
    public QuoteNotFound (long quoteId){
        super("ERR_DATA_NOT_FOUND", "Quote Not Found", quoteId);
    }
}