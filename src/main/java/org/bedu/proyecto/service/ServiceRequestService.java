package org.bedu.proyecto.service;

import java.util.List;

import org.bedu.proyecto.dto.servicerequest.CreateServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.request.RequestSameUserNotAllowed;
import org.bedu.proyecto.exception.request.ServiceRequestCreateNotAllowed;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.mapper.ServiceRequestMapper;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ServiceRequestService {
    @Autowired
    ServiceRequestRepository repository;

    @Autowired
    ServiceRequestMapper mapper;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Transactional
    public ServiceRequestDTO save(long clientId, CreateServiceRequestDTO data)
            throws ClientNotFoundException, ServiceNotFoundException,
            ServiceNotAssignedException, ServiceRequestCreateNotAllowed, RequestSameUserNotAllowed {
        Client client = Validation.clientExist(clientRepository, clientId);
        AppService service = Validation.serviceExist(serviceRepository, data.getServiceId());
        // Validation if Client haven´t done the same Request(Status = Open) to the same
        // Supplier
        List<ServiceRequest> existingRequests = repository.findAllByClient(client);
        if (!existingRequests.isEmpty()) {
            for (ServiceRequest existingRequest : existingRequests) {
                if (existingRequest.getService().getId() == service.getId()
                        & existingRequest.getStatus() == StatusRequest.OPEN) {
                    throw new ServiceRequestCreateNotAllowed(service.getId());
                }
            }
        }
        ServiceRequest entity = mapper.toModel(data);
        entity.setClient(client);
        entity.setService(service);
        entity.setUrgency(data.getUrgency());
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    public List<ServiceRequestDTO> findAllByClient(long clientId) throws ClientNotFoundException {
        Client client = Validation.clientExist(clientRepository, clientId);
        return mapper.toDTOs(repository.findAllByClient(client));
    }

}
