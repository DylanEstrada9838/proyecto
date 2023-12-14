package org.bedu.proyecto.exception.user;

import org.bedu.proyecto.exception.RuntimeException;

public class UserEmailAlreadyCreated extends RuntimeException{
    public UserEmailAlreadyCreated(String email) {
        super("ERR_DUPLICATE_ENTRY", "User with Email: " + email + " Already Exist", email);
    }
}

