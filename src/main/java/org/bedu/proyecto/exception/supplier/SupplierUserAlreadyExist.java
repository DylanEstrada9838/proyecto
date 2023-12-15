package org.bedu.proyecto.exception.supplier;

import org.bedu.proyecto.exception.RuntimeException;

public class SupplierUserAlreadyExist extends RuntimeException{
    public SupplierUserAlreadyExist(long userId) {
        super("ERR_DUPLICATE_ENTRY", "Supplier with user_id:" + userId + " already exist", userId);
    }
}
