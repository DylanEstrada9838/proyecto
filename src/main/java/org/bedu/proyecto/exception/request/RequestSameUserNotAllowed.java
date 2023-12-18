package org.bedu.proyecto.exception.request;

import org.bedu.proyecto.exception.RuntimeException;

public class RequestSameUserNotAllowed extends RuntimeException{
    public RequestSameUserNotAllowed ( long userId){
        super("ERR_BAD_REQUEST","Service request to same userId is not allowed", userId);
    }
}
