package org.bedu.proyecto.exception.authentication;

import org.bedu.proyecto.exception.RuntimeException;

public class UnauthorizedAction extends RuntimeException {
    public UnauthorizedAction(){
        super("ERR_FORBIDDEN", "Unathourized action", null);
    }
}
