package org.bedu.proyecto.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.bedu.proyecto.dto.address.AddressDTO;
import org.bedu.proyecto.dto.address.CreateAddressDTO;
import org.bedu.proyecto.dto.client.ClientDTO;
import org.bedu.proyecto.dto.client.CreateClientDTO;
import org.bedu.proyecto.dto.client.UpdateClientDTO;
import org.bedu.proyecto.dto.rating.CreateRatingDTO;
import org.bedu.proyecto.dto.servicerequest.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.exception.address.AddressNotAssignedToClient;
import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.client.ClientUserAlreadyExist;
import org.bedu.proyecto.exception.rating.RatingNotAlllowed;
import org.bedu.proyecto.exception.request.ServiceRequestCreateNotAllowed;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.service.AddressService;
import org.bedu.proyecto.service.ClientService;
import org.bedu.proyecto.service.RatingService;
import org.bedu.proyecto.service.ServiceRequestService;
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

@Tag(name="Endpoints de Clientes",description="CRUD de Clientes")
@RestController 
@RequestMapping("clients")  
public class ClientController {
    
    private ClientService service;

    private UserService userService;
 
    private ServiceRequestService serviceRequestService;

    private RatingService ratingService;
 
    private AddressService addressService;

    public ClientController(ClientService service, UserService userService, ServiceRequestService serviceRequestService,
            RatingService ratingService, AddressService addressService) {
        this.service = service;
        this.userService = userService;
        this.serviceRequestService = serviceRequestService;
        this.ratingService = ratingService;
        this.addressService = addressService;
    }

    @Operation(summary="Devuelve una lista de todos los clientes.")
    @GetMapping 
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDTO> findAll() {
        return service.findAll();
    }

    @Operation(summary="Devuelve el cliente con el ID especificado.")
    @GetMapping("{clientId}")  
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO findById(@PathVariable long clientId) throws ClientNotFoundException {
        return service.findById(clientId);
    }

    @Operation(summary="Crea un nuevo cliente con los datos proporcionados.")
    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO save(@Valid @RequestBody CreateClientDTO data) throws UserNotFoundException,ClientUserAlreadyExist{
        return service.save(data,userService.retrieveUserId());
    }

    @Operation(summary="Actualiza el cliente con el ID especificado.")
    @PutMapping("{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void update(@PathVariable long clientId, @Valid @RequestBody UpdateClientDTO data)
            throws ClientNotFoundException, UnauthorizedAction, UserNotFoundException {
                    service.update(clientId, data);
        
    }

    @Operation(summary="Elimina el cliente con el ID especificado.")
    @DeleteMapping("{clientId}") 
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delete(@PathVariable long clientId) throws ClientNotFoundException, UnauthorizedAction, UserNotFoundException {
            service.delete(clientId);
        
    }

    @Operation(summary = "Crea una solicitud de Servicio a un Proveedor específico.")
    @PostMapping("{clientId}/servicerequests")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceRequestDTO addServiceRequest(@PathVariable long clientId, @Valid @RequestBody CreateServiceRequestDTO data)
            throws ClientNotFoundException, ServiceNotFoundException,
             ServiceRequestCreateNotAllowed, AddressNotAssignedToClient, AddressNotFound {

        return serviceRequestService.save(clientId, data);
    }

    @Operation(summary = "Define un método para encontrar todas las solicitudes de servicio por cliente.")
    @GetMapping("{clientId}/servicerequests")
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceRequestDTO> findAllServiceRequestByClient(@PathVariable long clientId) throws ClientNotFoundException {
        return serviceRequestService.findAllByClient(clientId); 
    }

    @PostMapping("{clientId}/suppliers/{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRating(@PathVariable long clientId,@PathVariable long supplierId,@Valid @RequestBody CreateRatingDTO data) throws ServiceNotAssignedException, SupplierNotFoundException, ServiceNotFoundException, ClientNotFoundException, RatingNotAlllowed{
        ratingService.save(clientId, supplierId, data);
    }

    @PostMapping("{clientId}/addresses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AddressDTO addAddress(@PathVariable long clientId,@Valid @RequestBody CreateAddressDTO data) throws ClientNotFoundException{
        return addressService.save(clientId, data);
    }

    @GetMapping("{clientId}/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDTO> findAllAddresses(@PathVariable long clientId) throws ClientNotFoundException{
        return addressService.findAllByClient(clientId);
    }
}
