package org.bedu.proyecto.exception.appointment;

import org.bedu.proyecto.exception.RuntimeException;

public class AppointmentCreationNotAllowed extends RuntimeException{
    public AppointmentCreationNotAllowed(long quoteId){
        super("ERR_BAD_REQUEST", "Appointment cannot be created for Quote with differenet status than accepted", quoteId);
    }
}
