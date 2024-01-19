package org.bedu.proyecto.exception.supplier;

import org.bedu.proyecto.exception.RuntimeException;
import org.bedu.proyecto.keys.SupplierServiceKey;

public class SupplierServiceNotActive extends RuntimeException{
    public SupplierServiceNotActive(SupplierServiceKey key){
        super("ERR_BAD_REQUEST", "Service currently not active for this supplier", key);
    }
}
