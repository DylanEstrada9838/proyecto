package org.bedu.proyecto.service;

import java.util.Optional;

import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.exception.quote.QuoteAlreadyExist;
import org.bedu.proyecto.exception.quote_request.QuoteRequestNotFound;
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
    public QuoteDTO save(long quoteRequestId,CreateQuoteDTO data) throws QuoteAlreadyExist, QuoteRequestNotFound{
        log.info("data {}", data);
        Optional<QuoteRequest> quoteRequestOptional = quoteRequestRepository.findById(quoteRequestId);
        if (quoteRequestOptional.isEmpty()) {
            throw new QuoteRequestNotFound(quoteRequestId);
        }
        QuoteRequest quoteRequest = quoteRequestOptional.get();
        if(quoteRequest.getQuote()!=null){
            throw new QuoteAlreadyExist(quoteRequestId);
        }
        Quote entity = mapper.toModel(data);
        entity.setQuoteRequest(quoteRequest);
        repository.save(entity);

        return mapper.toDTO(entity);

    }
}

