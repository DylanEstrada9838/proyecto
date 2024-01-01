package org.bedu.proyecto.service;

import java.math.BigDecimal;
import java.util.Optional;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RatingService {
    @Autowired
    RatingRepository repository;
    @Autowired
    RatingMapper mapper;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    SupplierServiceJoinRepository supplierServiceJoinRepository;

    public void save(long clientId, long supplierId, CreateRatingDTO data) throws ServiceNotAssignedException,
            SupplierNotFoundException, ServiceNotFoundException, ClientNotFoundException, RatingNotAlllowed {
        // Validates existence
        Supplier supplier = Validation.supplierExist(supplierRepository, supplierId);
        AppService service = Validation.serviceExist(serviceRepository, data.getServiceId());
        Client client = Validation.clientExist(clientRepository, clientId);

        SupplierServiceKey supplierServiceKey = new SupplierServiceKey(supplier.getId(), service.getId());

        // Validates Service is provided by Supplier
        Optional<SupplierServiceJoin> supplierServiceJoinOptional = supplierServiceJoinRepository
                .findById(supplierServiceKey);
        if (supplierServiceJoinOptional.isEmpty()) {
            throw new ServiceNotAssignedException(service.getId());
        }
        // Validation Client is not rating itself as Supplier
        if (supplier.getUser().getId() == client.getUser().getId()) {
            throw new RatingNotAlllowed(supplierId);
        }

        repository.save(mapper.toModel(clientId, supplierId, data));
        
        // Set averageRating for SupplierServiceJoin
        BigDecimal averageRating = repository.calculateAverageRatingBySupplierServiceKey(supplierServiceKey);
        log.info("data {}", averageRating);
        SupplierServiceJoin supplierServiceJoin = supplierServiceJoinOptional.get();
        if (averageRating == null) {
            supplierServiceJoin.setAverageRating(new BigDecimal(data.getRating()).setScale(1));
        }
        if (averageRating != null) {
            supplierServiceJoin.setAverageRating(averageRating.setScale(1, RoundingMode.HALF_UP));
            supplierServiceJoinRepository.save(supplierServiceJoin);
        }
    }
}
