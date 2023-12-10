package org.bedu.proyecto.controller;

import java.util.List;

import org.bedu.proyecto.dto.SupplierDTO;
import org.bedu.proyecto.dto.AddServiceDTO;
import org.bedu.proyecto.dto.CreateSupplierDTO;
import org.bedu.proyecto.dto.RemoveServiceDTO;
import org.bedu.proyecto.dto.UpdateSupplierDTO;
import org.bedu.proyecto.exception.ServiceNotAssignedException;
import org.bedu.proyecto.exception.ServiceNotFoundException;
import org.bedu.proyecto.exception.SupplierNotFoundException;
import org.bedu.proyecto.exception.UserNotFoundException;
import org.bedu.proyecto.service.SupplierService;
import org.bedu.proyecto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("suppliers")
public class SupplierController {
    @Autowired
    SupplierService service;
    @Autowired
    UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SupplierDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("{supplierId}")
    @ResponseStatus(HttpStatus.OK)
    public SupplierDTO findById(@PathVariable long supplierId) throws SupplierNotFoundException {
        return service.findById(supplierId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierDTO save(@Valid @RequestBody CreateSupplierDTO data) throws UserNotFoundException{

        return service.save(data);
    }

    @PutMapping("{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long supplierId, @Valid @RequestBody UpdateSupplierDTO data)
            throws SupplierNotFoundException {
        service.update(supplierId, data);
    }

    @DeleteMapping("{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long supplierId) throws SupplierNotFoundException {
        service.delete(supplierId);
    }

    @PostMapping("{supplierId}/service")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addService(@PathVariable long supplierId,@RequestBody AddServiceDTO data) throws SupplierNotFoundException,ServiceNotFoundException{
        service.addService(supplierId, data.getServiceId());
    }

    @DeleteMapping("{supplierId}/service")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeService(@PathVariable long supplierId,@RequestBody RemoveServiceDTO data) throws SupplierNotFoundException,ServiceNotFoundException,ServiceNotAssignedException{
        service.removeService(supplierId, data.getServiceId());
    }


}
