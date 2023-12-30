package org.bedu.proyecto.exception.quote_request;

import org.bedu.proyecto.exception.RuntimeException;

public class QuoteRequestAcceptedExist extends RuntimeException {

    public QuoteRequestAcceptedExist(long serviceRequestId) {
        super("ERR_BAD_REQUEST", "An accepted Quote exists related to ServiceRequest",
                serviceRequestId);

    }
}