package org.bedu.proyecto.exception.user;

import org.bedu.proyecto.exception.RuntimeException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long userId) {
        super("ERR_DATA_NOT FOUND", "User " + userId + " Not Found", userId);
    }
}
