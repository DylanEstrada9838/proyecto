package org.bedu.proyecto.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long userId){
        super("ERR_DATA_NOT FOUND","User not found",userId);
    }
}
