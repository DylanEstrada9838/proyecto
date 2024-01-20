package org.bedu.proyecto.service;

import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.appointment.AppointmentNotFound;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.exception.quote_request.QuoteRequestNotFound;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.keys.SupplierServiceKey;
import org.bedu.proyecto.model.Address;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Appointment;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.AddressRepository;
import org.bedu.proyecto.repository.AppointmentRepository;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.QuoteRepository;
import org.bedu.proyecto.repository.QuoteRequestRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.SupplierServiceJoinRepository;
import org.bedu.proyecto.repository.UserRepository;

public class Validation {
    // Supplier
    public static Supplier supplierExists(SupplierRepository repository, long supplierId)
            throws SupplierNotFoundException {
        return repository.findById(supplierId).orElseThrow(() -> new SupplierNotFoundException(supplierId));
    }

    public static void verifySupplierExists(SupplierRepository repository, long supplierId)
            throws SupplierNotFoundException {
        if (!repository.existsById(supplierId)) {
            throw new SupplierNotFoundException(supplierId);
        }
    }

    // User
    public static User userExists(UserRepository repository, long userId) throws UserNotFoundException {
        return repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    // Service
    public static AppService serviceExists(ServiceRepository repository, long serviceId)
            throws ServiceNotFoundException {
        return repository.findById(serviceId).orElseThrow(() -> new ServiceNotFoundException(serviceId));
    }

    public static void verifyServiceExists(ServiceRepository repository, long serviceId)
            throws ServiceNotFoundException {
        if (!repository.existsById(serviceId)) {
            throw new ServiceNotFoundException(serviceId);
        }
    }

    // Client
    public static Client clientExists(ClientRepository repository, long clientId) throws ClientNotFoundException {
        return repository.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
    }

    public static void verifyClientExists(ClientRepository repository, long clientId) throws ClientNotFoundException {
        if (!repository.existsById(clientId)) {
            throw new ClientNotFoundException(clientId);
        }
    }

    // ServiceRequest
    public static ServiceRequest serviceRequestExists(ServiceRequestRepository repository, long serviceRequestId)
            throws ServiceRequestNotFound {
        return repository.findById(serviceRequestId)
                .orElseThrow(() -> new ServiceRequestNotFound(serviceRequestId));
    }

    // QuoteRequest
    public static QuoteRequest quoteRequestExists(QuoteRequestRepository repository, long quoteRequestId)
            throws QuoteRequestNotFound {
        return repository.findById(quoteRequestId).orElseThrow(() -> new QuoteRequestNotFound(quoteRequestId));
    }

    // Quote
    public static Quote quoteExists(QuoteRepository repository, long quoteId) throws QuoteNotFound {
        return repository.findById(quoteId).orElseThrow(() -> new QuoteNotFound(quoteId));
    }

    // Appointment
    public static Appointment appointmentExists(AppointmentRepository repository, long appointmentId)
            throws AppointmentNotFound {
        return repository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFound(appointmentId));
    }

    // SupplierServiceJoin
    public static SupplierServiceJoin supplierServiceJoinExists(SupplierServiceJoinRepository repository,
            SupplierServiceKey supplierServiceKey) throws ServiceNotAssignedException {
        return repository.findById(supplierServiceKey)
                .orElseThrow(() -> new ServiceNotAssignedException(supplierServiceKey.getServiceId()));
    }

    public static void verifySupplierServiceJoinExists(SupplierServiceJoinRepository repository,
            SupplierServiceKey supplierServiceKey) throws ServiceNotAssignedException {
        if (!repository.existsById(supplierServiceKey)) {
            throw new ServiceNotAssignedException(supplierServiceKey.getServiceId());
        }
    }

    // Address
    public static Address addressExists(AddressRepository repository, long addressId) throws AddressNotFound {
        return repository.findById(addressId).orElseThrow(() -> new AddressNotFound(addressId));
    }

}
