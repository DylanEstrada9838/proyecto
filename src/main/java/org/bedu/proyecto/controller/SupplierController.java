package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.dto.service.AddServiceDTO;
import org.bedu.proyecto.dto.service.RemoveServiceDTO;
import org.bedu.proyecto.dto.supplier.CreateSupplierDTO;
import org.bedu.proyecto.dto.supplier.SupplierDTO;
import org.bedu.proyecto.dto.supplier.UpdateSupplierDTO;
import org.bedu.proyecto.dto.supplier_service.ChangeStatusDTO;
import org.bedu.proyecto.dto.supplier_service.ServicesBySupplierDTO;
import org.bedu.proyecto.dto.supplier_service.SuppliersByServicesDTO;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceAlreadyAssignedException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.service.QuoteRequestService;
import org.bedu.proyecto.service.SupplierService;
import org.bedu.proyecto.service.UserService;
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

@Tag(name="Endpoints de Provedores y Servicios",description="CRUD de Usuarios y asignacion de servicios a provedores")
@RestController
@RequestMapping("suppliers") 
public class SupplierController {
     
    private SupplierService service;

    private UserService userService;
     
    private QuoteRequestService quoteRequestService;

    public SupplierController(SupplierService service, UserService userService,
            QuoteRequestService quoteRequestService) {
        this.service = service;
        this.userService = userService;
        this.quoteRequestService = quoteRequestService;
    }

    @Operation(summary="Este método devuelve una lista de todos los proveedores.")
    @GetMapping 
    @ResponseStatus(HttpStatus.OK) 
    public List<SupplierDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary="Este método devuelve un proveedor específico por su ID.")
    @GetMapping("{supplierId}") 
    @ResponseStatus(HttpStatus.OK)
    public SupplierDTO findById(@PathVariable long supplierId) throws SupplierNotFoundException {
        return service.findById(supplierId);
    }

    @Operation(summary="Este método guarda un nuevo proveedor en la base de datos.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) 
    public SupplierDTO save(@Valid @RequestBody CreateSupplierDTO data) throws UserNotFoundException,SupplierUserAlreadyExist{
        return service.save(data,userService.retrieveUserId());
    }

    @Operation(summary="Este método actualiza un proveedor existente en la base de datos deacuerdo a el ID de el path..")
    @PutMapping("{supplierId}") 
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void update(@PathVariable long supplierId, @Valid @RequestBody UpdateSupplierDTO data)
            throws SupplierNotFoundException, UnauthorizedAction, UserNotFoundException {
                    service.update(supplierId, data);
    }

    @Operation(summary="Este método elimina un proveedor existente en la base de datos deacuerdo a el ID de el path.")
    @DeleteMapping("{supplierId}") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long supplierId) throws SupplierNotFoundException, UnauthorizedAction, UserNotFoundException {
           service.delete(supplierId);
        
    }

    @Operation(summary="Este método agrega un servicio a un proveedor existente.")
    @PostMapping("{supplierId}/services") 
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void addService(@PathVariable long supplierId,@Valid @RequestBody AddServiceDTO data) throws SupplierNotFoundException,ServiceNotFoundException,ServiceAlreadyAssignedException{
        service.addService(supplierId, data);
    }

    @Operation(summary="Este método elimina un servicio de un proveedor existente.")
    @DeleteMapping("{supplierId}/services") 
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void removeService(@PathVariable long supplierId,@RequestBody RemoveServiceDTO data) throws SupplierNotFoundException,ServiceNotFoundException,ServiceNotAssignedException{
        service.removeService(supplierId, data.getServiceId());
    }

    @PutMapping("{supplierId}/services/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateService(@PathVariable long supplierId,@PathVariable long serviceId,@RequestBody ChangeStatusDTO status) throws SupplierNotFoundException, ServiceNotFoundException, ServiceNotAssignedException{
        service.changeServiceStatus(status, supplierId, serviceId);
    }
    

    @Operation(summary="Este método devuelve una lista de todos los servicios de un proveedor específico.")
    @GetMapping("{supplierId}/services") // Mapea las solicitudes GET con un ID de proveedor y "/services" a este método.
    @ResponseStatus(HttpStatus.OK) // Si el método se ejecuta con éxito, devuelve un estado HTTP 200 (OK).
    public List<ServicesBySupplierDTO> findAllServicesBySupplier(@PathVariable long supplierId) throws SupplierNotFoundException{
        return service.findAllBySupplier(supplierId);
    }

    @GetMapping("services/{serviceId}")
    @ResponseStatus(HttpStatus.OK)
    public List<SuppliersByServicesDTO> findAllSuppliersByService(@PathVariable long serviceId) throws ServiceNotFoundException{
        return service.findAllByService(serviceId);
    }

    @GetMapping("{supplierId}/quoterequests")
    @ResponseStatus(HttpStatus.OK)
    public List<QuoteRequestDTO> findAllQuoteRequestBySupplier(@PathVariable long supplierId) throws SupplierNotFoundException{
        return quoteRequestService.findAllBySupplier(supplierId);
    }
    
}
