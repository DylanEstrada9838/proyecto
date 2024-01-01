package org.bedu.proyecto.exception.rating;

import org.bedu.proyecto.exception.RuntimeException;

public class RatingNotAlllowed extends RuntimeException{
    public RatingNotAlllowed(long userId){
        super("ERR_BAD_REQUEST", "Rating Not Allowed", userId);
    }
    
}
