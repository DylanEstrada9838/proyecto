package org.bedu.proyecto.exception.authentication;

import org.bedu.proyecto.exception.RuntimeException;
import org.springframework.security.authentication.BadCredentialsException;

public class UserOrPasswordIncorrect extends RuntimeException{
    public UserOrPasswordIncorrect(BadCredentialsException e){
        super("ERR_FORBIDDEN", "Email or Password is incorrect", e.getMessage());
    }
    
}
