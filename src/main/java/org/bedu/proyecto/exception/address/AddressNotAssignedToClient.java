package org.bedu.proyecto.exception.address;

import org.bedu.proyecto.exception.RuntimeException;

public class AddressNotAssignedToClient extends RuntimeException {
    public AddressNotAssignedToClient(long addressId){
        super("ERR_BAD_REQUEST", "Address does not belong to specified client", addressId);
    }
}
