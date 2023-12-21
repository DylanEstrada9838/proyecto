package org.bedu.proyecto.exception.appointment;

import org.bedu.proyecto.exception.RuntimeException;

public class AppointmentNotFound extends RuntimeException{
    public AppointmentNotFound (long quoteId){
        super("ERR_DATA_NOT_FOUND", "Appointment Not Found", quoteId);
    }
}
