package org.bedu.proyecto.exception.quote_request;
import org.bedu.proyecto.exception.RuntimeException;

public class QuoteRequestClosed extends RuntimeException {
    public QuoteRequestClosed(long quoteRequestId){
        super("ERR_BAD_REQUEST", "QuoteRequest already CLOSED", quoteRequestId);
    }
    
}
