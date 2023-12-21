package org.bedu.proyecto.service;


import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.exception.quote.QuoteAlreadyExist;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.exception.quote_request.QuoteRequestNotFound;
import org.bedu.proyecto.mapper.QuoteMapper;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.repository.QuoteRepository;
import org.bedu.proyecto.repository.QuoteRequestRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public QuoteDTO save(long quoteRequestId, CreateQuoteDTO data) throws QuoteAlreadyExist, QuoteRequestNotFound {
        log.info("data {}", data);
        QuoteRequest quoteRequest = Validation.quoteRequestExist(quoteRequestRepository, quoteRequestId);
        if (quoteRequest.getQuote() != null) {
            throw new QuoteAlreadyExist(quoteRequestId);
        }
        Quote entity = mapper.toModel(data);
        entity.setQuoteRequest(quoteRequest);
        repository.save(entity);

        return mapper.toDTO(entity);

    }

    public QuoteDTO findByQuoteRequest(long quoteRequestId) throws QuoteRequestNotFound, QuoteNotFound{
        QuoteRequest quoteRequest = Validation.quoteRequestExist(quoteRequestRepository, quoteRequestId);
        if (quoteRequest.getQuote()==null){
            throw new QuoteNotFound(quoteRequestId);
        }
        return mapper.toDTO(quoteRequest.getQuote());
    }
}
