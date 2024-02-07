package org.bedu.proyecto.service;

import java.util.List;

import org.bedu.proyecto.dto.quote_request.CreateQuoteRequestDTO;
import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.exception.quote_request.QuoteRequestAcceptedExist;
import org.bedu.proyecto.exception.quote_request.QuoteRequestAlreadyExist;
import org.bedu.proyecto.exception.request.RequestSameUserNotAllowed;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierServiceNotActive;
import org.bedu.proyecto.keys.SupplierServiceKey;
import org.bedu.proyecto.mapper.QuoteRequestMapper;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.StatusSupplierServiceJoin;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.QuoteRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.SupplierServiceJoinRepository;
import org.springframework.stereotype.Service;

@Service
public class QuoteRequestService {
    
    private QuoteRequestRepository repository;
    
    private QuoteRequestMapper mapper;
    
    private SupplierRepository supplierRepository;
    
    private ServiceRequestRepository serviceRequestRepository;
    
    private SupplierServiceJoinRepository supplierServiceJoinRepository;

    public QuoteRequestService(QuoteRequestRepository repository, QuoteRequestMapper mapper,
            SupplierRepository supplierRepository, ServiceRequestRepository serviceRequestRepository,
            SupplierServiceJoinRepository supplierServiceJoinRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.supplierRepository = supplierRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.supplierServiceJoinRepository = supplierServiceJoinRepository;
    }

    public QuoteRequestDTO save(long serviceRequestId, CreateQuoteRequestDTO data)
            throws ServiceRequestNotFound, SupplierNotFoundException, RequestSameUserNotAllowed,
            ServiceNotAssignedException, QuoteRequestAlreadyExist, QuoteRequestAcceptedExist, SupplierServiceNotActive {

        ServiceRequest serviceRequest = Validation.serviceRequestExists(serviceRequestRepository, serviceRequestId);
        Supplier supplier = Validation.supplierExists(supplierRepository, data.getSupplierId());
        // Validation Client has not the same userId as Supplier
        if (supplier.getUser().getId() == serviceRequest.getClient().getUser().getId()) {
            throw new RequestSameUserNotAllowed(serviceRequest.getClient().getUser().getId());
        }
        // SupplierServiceKey
        SupplierServiceKey key = new SupplierServiceKey(data.getSupplierId(), serviceRequest.getService().getId());
        // Validation if service is assigned to selected Supplier
        SupplierServiceJoin supplierServiceJoin = Validation.supplierServiceJoinExists(supplierServiceJoinRepository,
                key);
        // Validation Supplier has ACTIVE its Service
        if (supplierServiceJoin.getStatus() != StatusSupplierServiceJoin.ACTIVE) {
            throw new SupplierServiceNotActive(key);
        }

        // Validation ServiceRequest is not in ASSIGNED Status, meaning there is already
        // a Quote with ACCEPTED status
        // so the creation of a Quote Request is not allowed
        if (serviceRequest.getStatus() == StatusRequest.ASSIGNED
                || serviceRequest.getStatus() == StatusRequest.SCHEDULED
                || serviceRequest.getStatus() == StatusRequest.COMPLETED) {
            throw new QuoteRequestAcceptedExist(serviceRequest.getId());
        }

        // Gets all existing QuoteRequests
        List<QuoteRequest> existingQuoteRequests = repository.findAllByServiceRequest(serviceRequest);

        // Only for first QuoteRequest change ServiceRequest Status to IN_PROCESS
        if (existingQuoteRequests.isEmpty()) {
            serviceRequest.setStatus(StatusRequest.IN_PROCESS);
            serviceRequestRepository.save(serviceRequest);
        }

        // Validation if there is already an existing Quote Request to same Supplier
        if (!existingQuoteRequests.isEmpty()) {
            for (QuoteRequest existingQuoteRequest : existingQuoteRequests) {
                if (existingQuoteRequest.getSupplier().getId() == supplier.getId()) {
                    throw new QuoteRequestAlreadyExist(serviceRequest.getId(), supplier.getId());
                }
            }
        }

        QuoteRequest entity = mapper.toModel(data);
        entity.setServiceRequest(serviceRequest);
        repository.save(entity);

        return mapper.toDTO(entity);

    }

    public List<QuoteRequestDTO> findAllBySupplier(long supplierId) throws SupplierNotFoundException {
        Supplier supplier = Validation.supplierExists(supplierRepository, supplierId);
        return mapper.toDTOs(repository.findAllBySupplier(supplier));
    }

    public List<QuoteRequestDTO> findAllByServiceRequest(long serviceRequestId) throws ServiceRequestNotFound {
        ServiceRequest serviceRequest = Validation.serviceRequestExists(serviceRequestRepository, serviceRequestId);
        return mapper.toDTOs(repository.findAllByServiceRequest(serviceRequest));
    }

}
