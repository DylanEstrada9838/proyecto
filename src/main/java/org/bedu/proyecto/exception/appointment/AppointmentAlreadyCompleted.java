package org.bedu.proyecto.exception.appointment;

import org.bedu.proyecto.exception.RuntimeException;

public class AppointmentAlreadyCompleted extends RuntimeException{
    public AppointmentAlreadyCompleted(long appointmentId){
        super("ERR_BAD_REQUEST", "Appointment already completed", appointmentId);
    }
    
}
