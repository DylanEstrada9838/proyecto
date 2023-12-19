package org.bedu.proyecto.service;

import java.util.Optional;

import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.exception.quote.QuoteServiceRequestNotAllowed;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.mapper.QuoteMapper;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.repository.QuoteRepository;
import org.bedu.proyecto.repository.QuoteRequestRepository;
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
    QuoteRequestRepository quoteRequestRepository;
    @Transactional
    public QuoteDTO save(long supplierId,long requestId,CreateQuoteDTO data) throws ServiceRequestNotFound,SupplierNotFoundException,QuoteServiceRequestNotAllowed{
        log.info("data {}", data);
        Optional<QuoteRequest> quoteRequestOptional = quoteRequestRepository.findById(requestId);
        if (quoteRequestOptional.isEmpty()) {
            throw new ServiceRequestNotFound(requestId);
        }
        QuoteRequest request = quoteRequestOptional.get();
        if(request.getSupplier().getId() != supplierId){
            throw new QuoteServiceRequestNotAllowed(requestId);
        }
        Quote entity = mapper.toModel(data);
        entity.setQuoteRequest(request);
        repository.save(entity);

        return mapper.toDTO(entity);

    }
}

