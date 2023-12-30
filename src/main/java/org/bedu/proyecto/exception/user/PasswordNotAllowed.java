package org.bedu.proyecto.exception.user;

import org.bedu.proyecto.exception.RuntimeException;

public class PasswordNotAllowed extends RuntimeException{
    public PasswordNotAllowed (long userId){
        super("ERR_BAD_REQUEST", "Password cannot be the same as before", userId);
    }
    
}
