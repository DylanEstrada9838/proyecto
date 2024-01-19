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
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceAlreadyAssignedException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.service.QuoteRequestService;
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

@Tag(name="Endpoints de Provedores y Servicios",description="CRUD de Usuarios y asignacion de servicios a provedores")
@RestController // Esta anotación indica que esta clase es un controlador REST.
@RequestMapping("suppliers") // Esta anotación mapea las solicitudes HTTP a "/suppliers" a los métodos en esta clase.
public class SupplierController {
    @Autowired // Esta anotación permite la inyección automática del bean 'SupplierService'.
    SupplierService service;
    @Autowired // Esta anotación permite la inyección automática del bean 'UserService'.
    UserService userService;
    @Autowired 
    QuoteRequestService quoteRequestService;

    @Operation(summary="Este método devuelve una lista de todos los proveedores.")
    @GetMapping // Mapea las solicitudes GET a este método.
    @ResponseStatus(HttpStatus.OK) // Si el método se ejecuta con éxito, devuelve un estado HTTP 200 (OK).
    public List<SupplierDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary="Este método devuelve un proveedor específico por su ID.")
    @GetMapping("{supplierId}") // Mapea las solicitudes GET con un ID de proveedor a este método.
    @ResponseStatus(HttpStatus.OK) // Si el método se ejecuta con éxito, devuelve un estado HTTP 200 (OK).
    public SupplierDTO findById(@PathVariable long supplierId) throws SupplierNotFoundException {
        return service.findById(supplierId);
    }

    @Operation(summary="Este método guarda un nuevo proveedor en la base de datos.")
    @PostMapping // Mapea las solicitudes POST a este método.
    @ResponseStatus(HttpStatus.CREATED) // Si el método se ejecuta con éxito, devuelve un estado HTTP 201 (CREADO).
    public SupplierDTO save(@Valid @RequestBody CreateSupplierDTO data) throws UserNotFoundException,SupplierUserAlreadyExist{
        return service.save(data);
    }

    @Operation(summary="Este método actualiza un proveedor existente en la base de datos deacuerdo a el ID de el path..")
    @PutMapping("{supplierId}") // Mapea las solicitudes PUT con un ID de proveedor a este método.
    @ResponseStatus(HttpStatus.NO_CONTENT) // Si el método se ejecuta con éxito, pero no va a devolver ningún contenido HTTP 204 (NO CONTENT).
    public void update(@PathVariable long supplierId, @Valid @RequestBody UpdateSupplierDTO data)
            throws SupplierNotFoundException {
        service.update(supplierId, data);
    }

    @Operation(summary="Este método elimina un proveedor existente en la base de datos deacuerdo a el ID de el path.")
    @DeleteMapping("{supplierId}") // Mapea las solicitudes DELETE con un ID de proveedor a este método.
    @ResponseStatus(HttpStatus.NO_CONTENT) // Si el método se ejecuta con éxito, pero no va a devolver ningún contenido HTTP 204 (NO CONTENT).
    public void delete(@PathVariable long supplierId) throws SupplierNotFoundException {
        service.delete(supplierId);
    }

    @Operation(summary="Este método agrega un servicio a un proveedor existente.")
    @PostMapping("{supplierId}/services") // Mapea las solicitudes POST con un ID de proveedor y "/services" a este método.
    @ResponseStatus(HttpStatus.NO_CONTENT) // Si el método se ejecuta con éxito, pero no va a devolver ningún contenido HTTP 204 (NO CONTENT).
    public void addService(@PathVariable long supplierId,@Valid @RequestBody AddServiceDTO data) throws SupplierNotFoundException,ServiceNotFoundException,ServiceAlreadyAssignedException{
        service.addService(supplierId, data);
    }

    @Operation(summary="Este método elimina un servicio de un proveedor existente.")
    @DeleteMapping("{supplierId}/services") // Mapea las solicitudes DELETE con un ID de proveedor y "/services" a este método.
    @ResponseStatus(HttpStatus.NO_CONTENT) // Si el método se ejecuta con éxito, pero no va a devolver ningún contenido HTTP 204 (NO CONTENT).
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
    List<SuppliersByServicesDTO> findAllSuppliersByService(@PathVariable long serviceId) throws ServiceNotFoundException{
        return service.findAllByService(serviceId);
    }

    @GetMapping("{supplierId}/quoterequests")
    @ResponseStatus(HttpStatus.OK)
    public List<QuoteRequestDTO> findAllQuoteRequestBySupplier(@PathVariable long supplierId) throws SupplierNotFoundException{
        return quoteRequestService.findAllBySupplier(supplierId);
    }
    
}
