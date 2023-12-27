package org.bedu.proyecto.exception.quote;

import org.bedu.proyecto.exception.RuntimeException;

public class CannotChangeQuoteStatus extends RuntimeException {
    public CannotChangeQuoteStatus(long quoteId){
        super("ERR_BAD_REQUEST", "Cannot change QuoteStatus", quoteId);
    }
    
}
