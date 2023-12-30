package org.bedu.proyecto.exception.appointment;

import org.bedu.proyecto.exception.RuntimeException;

public class AppointmentCreationNotAllowed extends RuntimeException{
    public AppointmentCreationNotAllowed(long quoteId){
        super("ERR_BAD_REQUEST", "Quote is not with status accepted", quoteId);
    }
}
