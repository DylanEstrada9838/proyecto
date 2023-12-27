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
import org.bedu.proyecto.mapper.QuoteRequestMapper;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model_enums.StatusQuote;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.QuoteRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.SupplierServiceJoinRepository;
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

    @Autowired
    SupplierServiceJoinRepository supplierServiceJoinRepository;

    public QuoteRequestDTO save(long serviceRequestId, CreateQuoteRequestDTO data)
            throws ServiceRequestNotFound, SupplierNotFoundException, RequestSameUserNotAllowed,
            ServiceNotAssignedException, QuoteRequestAlreadyExist, QuoteRequestAcceptedExist {

        ServiceRequest serviceRequest = Validation.serviceRequestExist(serviceRequestRepository, serviceRequestId);
        Supplier supplier = Validation.supplierExist(supplierRepository, data.getSupplierId());
        // Validation Client has not the same userId as Supplier
        if (supplier.getUser().getId() == serviceRequest.getClient().getUser().getId()) {
            throw new RequestSameUserNotAllowed(serviceRequest.getClient().getUser().getId());
        }
        List<AppService> services = supplierServiceJoinRepository.findServicesBySupplier(data.getSupplierId());
        // Validation if service is assigned to selected Supplier

        if (!services.contains(serviceRequest.getService())) {
            throw new ServiceNotAssignedException(serviceRequest.getService().getId());
        }

        // Validation if there is already an existing Quote Request for the Service
        // Request to same Supplier
        List<QuoteRequest> existingQuoteRequests = repository.findAllByServiceRequest(serviceRequest);
        if (!existingQuoteRequests.isEmpty()) {
            for (QuoteRequest existingQuoteRequest : existingQuoteRequests) {
                if (existingQuoteRequest.getSupplier().getId() == supplier.getId()
                        & serviceRequest.getStatus() == StatusRequest.PENDING) {
                    throw new QuoteRequestAlreadyExist(serviceRequest.getId(), supplier.getId());
                }
                // Validation there is not a Quote already in status ACCEPTED for ServiceRequest
                if (existingQuoteRequest.getQuote() != null) {
                    if (existingQuoteRequest.getQuote().getStatus() == StatusQuote.ACCEPTED
                            || existingQuoteRequest.getQuote().getStatus() == StatusQuote.ASSIGNED) {
                        throw new QuoteRequestAcceptedExist(existingQuoteRequest.getId());
                    }
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
        Supplier supplier = Validation.supplierExist(supplierRepository, supplierId);
        return mapper.toDTOs(repository.findAllBySupplier(supplier));
    }

    public List<QuoteRequestDTO> findAllByServiceRequest(long serviceRequestId) throws ServiceRequestNotFound {
        ServiceRequest serviceRequest = Validation.serviceRequestExist(serviceRequestRepository, serviceRequestId);
        return mapper.toDTOs(repository.findAllByServiceRequest(serviceRequest));
    }

}
