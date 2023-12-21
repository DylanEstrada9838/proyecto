package org.bedu.proyecto.exception.appointment;

import org.bedu.proyecto.exception.RuntimeException;

public class AppointmentAlreadyExist extends RuntimeException{
    public AppointmentAlreadyExist (long quoteId){
        super("ERR_DUPLICATE_ENTRY", "Appointment for Quote already exist", quoteId);
    }
}
