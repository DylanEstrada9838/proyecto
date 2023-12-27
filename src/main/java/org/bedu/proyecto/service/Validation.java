package org.bedu.proyecto.service;

import java.util.Optional;

import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.exception.quote_request.QuoteRequestNotFound;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.QuoteRepository;
import org.bedu.proyecto.repository.QuoteRequestRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.UserRepository;

public class Validation {
    public static Supplier supplierExist(SupplierRepository repository, long supplierId) throws SupplierNotFoundException {
        Optional<Supplier> supplierOptional = repository.findById(supplierId);
        return supplierOptional.orElseThrow(() -> new SupplierNotFoundException(supplierId));
    }
    public static void verifySupplierExists(SupplierRepository repository, long supplierId) throws SupplierNotFoundException {
        if (repository.existsById(supplierId)) {
            // Supplier exists, do nothing
        } else {
            throw new SupplierNotFoundException(supplierId);
        }
    }
    
    public static User userExist(UserRepository repository, long userId) throws UserNotFoundException {
        Optional<User> userOptional = repository.findById(userId);
        return userOptional.orElseThrow(() -> new UserNotFoundException(userId));
    }
    public static AppService serviceExist(ServiceRepository repository, long serviceId) throws ServiceNotFoundException {
       Optional<AppService> serviceOptional = repository.findById(serviceId);
        return serviceOptional.orElseThrow(() -> new ServiceNotFoundException(serviceId));
    }
    public static void verifyServiceExists(ServiceRepository repository, long serviceId) throws ServiceNotFoundException {
        if (repository.existsById(serviceId)) {
            // Service exists, do nothing
        } else {
            throw new ServiceNotFoundException(serviceId);
        }
    }
    public static Client clientExist(ClientRepository repository,long clientId) throws ClientNotFoundException{
        Optional<Client> clientOptional = repository.findById(clientId);
        return clientOptional.orElseThrow(() -> new ClientNotFoundException(clientId));
    }
    public static ServiceRequest serviceRequestExist(ServiceRequestRepository repository,long serviceRequestId) throws ServiceRequestNotFound{
        Optional<ServiceRequest> serviceRequestOptional = repository.findById(serviceRequestId);
        return serviceRequestOptional
                .orElseThrow(() -> new ServiceRequestNotFound(serviceRequestId));
    }
    public static QuoteRequest quoteRequestExist(QuoteRequestRepository repository,long quoteRequestId) throws QuoteRequestNotFound{
        Optional<QuoteRequest> quoteRequestOptional = repository.findById(quoteRequestId);
        return quoteRequestOptional.orElseThrow(() -> new QuoteRequestNotFound(quoteRequestId));
    }
    public static Quote quoteExist(QuoteRepository repository,long quoteId) throws QuoteNotFound{
        Optional<Quote> quoteOptional = repository.findById(quoteId);
        return quoteOptional.orElseThrow(() -> new QuoteNotFound(quoteId));
    }
       


   

}
