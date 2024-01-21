package org.bedu.proyecto.exception.user;

import org.bedu.proyecto.exception.RuntimeException;

public class UserEmailNotFound extends RuntimeException {
    public UserEmailNotFound(String email){
        super("ERR_DATA_NOT_FOUND", "User with email Not Found", email);
    }
}
