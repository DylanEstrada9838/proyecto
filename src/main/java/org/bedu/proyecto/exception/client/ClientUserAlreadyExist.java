package org.bedu.proyecto.exception.client;

import org.bedu.proyecto.exception.RuntimeException;

public class ClientUserAlreadyExist extends RuntimeException{
    public ClientUserAlreadyExist(long userId) {
        super("ERR_DUPLICATE_ENTRY", "Client with user_id:" + userId + " already exist", userId);
    }
}
