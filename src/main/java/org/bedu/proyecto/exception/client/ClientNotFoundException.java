package org.bedu.proyecto.exception.client;

import org.bedu.proyecto.exception.RuntimeException;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(long clientId) {
        super("ERR_DATA_NOT FOUND", "Client " + clientId + " Not Found", clientId);
    }
}
