package org.bedu.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.quote_request.CreateQuoteRequestDTO;
import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.exception.quote_request.QuoteRequestAlreadyExist;
import org.bedu.proyecto.exception.request.RequestSameUserNotAllowed;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.mapper.QuoteRequestMapper;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.QuoteRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteRequestService {

    @Autowired
    QuoteRequestRepository repository;

    @Autowired
    QuoteRequestMapper mapper;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ServiceRequestRepository serviceRequestRepository;

    public QuoteRequestDTO save(long serviceRequestId, CreateQuoteRequestDTO data)
            throws ServiceRequestNotFound, SupplierNotFoundException, RequestSameUserNotAllowed,
            ServiceNotAssignedException, QuoteRequestAlreadyExist {

        Optional<ServiceRequest> serviceRequestOptional = serviceRequestRepository.findById(serviceRequestId);
        ServiceRequest serviceRequest = serviceRequestOptional
                .orElseThrow(() -> new ServiceRequestNotFound(serviceRequestId));

        Optional<Supplier> supplierOptional = supplierRepository.findById(data.getSupplierId());
        Supplier supplier = supplierOptional.orElseThrow(() -> new SupplierNotFoundException(data.getSupplierId()));

        // Validation Client has not the same userId as Supplier
        if (supplier.getUser().getId() == serviceRequest.getClient().getUser().getId()) {
            throw new RequestSameUserNotAllowed(serviceRequest.getClient().getUser().getId());
        }

        // Validation if service is assigned to selected Supplier
        List<AppService> listServices = supplier.getServices();
        if (!listServices.contains(serviceRequest.getService())) {
            throw new ServiceNotAssignedException(serviceRequest.getService().getId());
        }
        // Validation if there is already an existing Quote Request for the Service
        // Request to same Supplier
        List<QuoteRequest> existingQuoteRequests = repository.findAllByServiceRequest(serviceRequest);
        if (!existingQuoteRequests.isEmpty()) {
            for (QuoteRequest existingQuoteRequest : existingQuoteRequests) {
                if (existingQuoteRequest.getSupplier().getId() == supplier.getId()
                        & serviceRequest.getStatus() == StatusRequest.OPEN) {
                    throw new QuoteRequestAlreadyExist(serviceRequest.getId(), supplier.getId());
                }
            }
        }

        QuoteRequest entity = mapper.toModel(data);

        entity.setServiceRequest(serviceRequest);
        entity.setSupplier(supplier);
        repository.save(entity);
        return mapper.toDTO(entity);

    }

    public List<QuoteRequestDTO> findAllBySupplier(long supplierId) throws SupplierNotFoundException {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        Supplier supplier = supplierOptional.orElseThrow(() -> new SupplierNotFoundException(supplierId));

        return mapper.toDTOs(repository.findAllBySupplier(supplier));
    }

    public List<QuoteRequestDTO> findAllByServiceRequest(long serviceRequestId) throws ServiceRequestNotFound {
        Optional<ServiceRequest> serviceRequestOptional = serviceRequestRepository.findById(serviceRequestId);
        ServiceRequest serviceRequest = serviceRequestOptional.orElseThrow(() -> new ServiceRequestNotFound(serviceRequestId));

        return mapper.toDTOs(repository.findAllByServiceRequest(serviceRequest));
    }

}
