package org.bedu.proyecto.exception.address;

import org.bedu.proyecto.exception.RuntimeException;

public class AddressNotFound extends RuntimeException{
    public AddressNotFound (long addressId){
        super("ERR_DATA_NOT_FOUND", "Address Not Found", addressId);
    }
    
}
