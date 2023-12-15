package org.bedu.proyecto.service;

import java.util.Optional;

import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.mapper.QuoteMapper;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.repository.QuoteRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class QuoteService {
    @Autowired
    QuoteRepository repository;
    @Autowired
    QuoteMapper mapper;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ServiceRequestRepository requestRepository;
    @Transactional
    public QuoteDTO save(long supplierId,long requestId,CreateQuoteDTO data){
        log.info("data {}", data);
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        Optional<ServiceRequest> requestOptional = requestRepository.findById(requestId);
        Quote entity = mapper.toModel(data);
        log.info("entity {}", entity);
        entity.setSupplier(supplierOptional.get());
        entity.setServiceRequest(requestOptional.get());
        repository.save(entity);

        return mapper.toDTO(entity);

    }
}
