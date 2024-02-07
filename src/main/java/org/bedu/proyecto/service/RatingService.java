package org.bedu.proyecto.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.bedu.proyecto.dto.rating.CreateRatingDTO;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.rating.RatingNotAlllowed;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.keys.SupplierServiceKey;
import org.bedu.proyecto.mapper.RatingMapper;
import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.SupplierServiceJoin;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.RatingRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.SupplierServiceJoinRepository;
import org.springframework.stereotype.Service;


@Service
public class RatingService {
    
    private RatingRepository repository;
    
    private RatingMapper mapper;
    
    private SupplierRepository supplierRepository;
    
    private ServiceRepository serviceRepository;
    
    private ClientRepository clientRepository;
    
    private SupplierServiceJoinRepository supplierServiceJoinRepository;

    public RatingService(RatingRepository repository, RatingMapper mapper, SupplierRepository supplierRepository,
            ServiceRepository serviceRepository, ClientRepository clientRepository,
            SupplierServiceJoinRepository supplierServiceJoinRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.supplierRepository = supplierRepository;
        this.serviceRepository = serviceRepository;
        this.clientRepository = clientRepository;
        this.supplierServiceJoinRepository = supplierServiceJoinRepository;
    }


    public void save(long clientId, long supplierId, CreateRatingDTO data) throws ServiceNotAssignedException,
            SupplierNotFoundException, ServiceNotFoundException, ClientNotFoundException, RatingNotAlllowed {
        // Validates existence
        Supplier supplier = Validation.supplierExists(supplierRepository, supplierId);
        AppService service = Validation.serviceExists(serviceRepository, data.getServiceId());
        Client client = Validation.clientExists(clientRepository, clientId);

        SupplierServiceKey supplierServiceKey = new SupplierServiceKey(supplier.getId(), service.getId());

        // Validates Service is provided by Supplier
        SupplierServiceJoin supplierServiceJoin = Validation.supplierServiceJoinExists(supplierServiceJoinRepository, supplierServiceKey);
        // Validation Client is not rating itself as Supplier
        if (supplier.getUser().getId() == client.getUser().getId()) {
            throw new RatingNotAlllowed(supplierId);
        }

        repository.save(mapper.toModel(clientId, supplierId, data));

        // Set averageRating and count for SupplierServiceJoin
        BigDecimal averageRating = repository.calculateAverageRatingBySupplierServiceKey(supplierServiceKey);
        Integer countRating = repository.calculateCountRatingBySupplierServiceKey(supplierServiceKey);
        supplierServiceJoin.setAverageRating(averageRating.setScale(1, RoundingMode.HALF_UP));
        supplierServiceJoin.setCountRating(countRating);
        supplierServiceJoinRepository.save(supplierServiceJoin);
    }
}
