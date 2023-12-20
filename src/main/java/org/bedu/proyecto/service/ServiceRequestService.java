package org.bedu.proyecto.service;

import java.util.List;
import java.util.Optional;

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
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        Optional<AppService> serviceOptional = serviceRepository.findById(data.getServiceId());
        

        if (clientOptional.isEmpty()) {
            throw new ClientNotFoundException(clientId);
        }
        if (serviceOptional.isEmpty()) {
            throw new ServiceNotFoundException(data.getServiceId());
        }
        
       
        AppService service = serviceOptional.get();
        Client client = clientOptional.get();
        // // Validation Client has not the same userId as Supplier
        // if (supplier.getUser().getId() == client.getUser().getId()) {
        //     throw new RequestSameUserNotAllowed(client.getUser().getId());
        // }

        // // Validation if service is assigned to selected Supplier
        // List<AppService> listServices = supplier.getServices();
        // if (!listServices.contains(service)) {
        //     throw new ServiceNotAssignedException(service.getId());
        // }
        // Validation if Client haven´t done the same Request(Status = Open) to the same
        // Supplier
        List<ServiceRequestDTO> existingRequests = mapper.toDTOs(repository.findAllByClient(client));
        if (!existingRequests.isEmpty()) {
            for (ServiceRequestDTO existingRequest : existingRequests) {
                if (existingRequest.getServiceId() == service.getId()
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
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        if (clientOptional.isEmpty()) {
            throw new ClientNotFoundException(clientId);
        }

        return mapper.toDTOs(repository.findAllByClient(clientOptional.get()));
    }

}
