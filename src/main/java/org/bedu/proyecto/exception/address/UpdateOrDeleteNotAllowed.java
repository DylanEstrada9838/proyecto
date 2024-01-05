package org.bedu.proyecto.exception.address;

import org.bedu.proyecto.exception.RuntimeException;

public class UpdateOrDeleteNotAllowed extends RuntimeException{
    public UpdateOrDeleteNotAllowed (long addressId){
        super("ERR_BAD_REQUEST", "Update or Delete for address not allowed", addressId);
    }
}
