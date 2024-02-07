package org.bedu.proyecto.controller;

import org.bedu.proyecto.dto.address.UpdateAddressDTO;
import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.address.UpdateOrDeleteNotAllowed;
import org.bedu.proyecto.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("addresses")
public class AddressController {

    
    private AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @PutMapping("{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long addressId,@RequestBody UpdateAddressDTO data) throws AddressNotFound, UpdateOrDeleteNotAllowed{
        service.update(addressId, data);
    }

    @DeleteMapping("{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long addressId) throws AddressNotFound, UpdateOrDeleteNotAllowed{
        service.delete(addressId);
    }
    
}
