package org.bedu.proyecto.exception.appointment;

import org.bedu.proyecto.exception.RuntimeException;

public class CannotChangeStatus extends RuntimeException {
    public CannotChangeStatus (long appointmentId){
        super("ERR_BAD_REQUEST", "Cannot change appointment status", appointmentId);
    }
}
