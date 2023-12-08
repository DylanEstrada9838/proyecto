package org.bedu.proyecto.exception;


public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(long clientId){
        super("ERR_DATA_NOT FOUND","Client not found",clientId);
    }
}
