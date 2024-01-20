package org.bedu.proyecto.service;

import java.util.List;

import org.bedu.proyecto.dto.quote.ChangeStatusQuoteDTO;
import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.exception.quote.CannotChangeQuoteStatus;
import org.bedu.proyecto.exception.quote.QuoteAlreadyExist;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.exception.quote_request.QuoteRequestClosed;
import org.bedu.proyecto.exception.quote_request.QuoteRequestNotFound;
import org.bedu.proyecto.mapper.QuoteMapper;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model_enums.StatusQuote;
import org.bedu.proyecto.model_enums.StatusQuoteRequest;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.repository.QuoteRepository;
import org.bedu.proyecto.repository.QuoteRequestRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
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
    @Autowired
    ServiceRequestRepository serviceRequestRepository;

    public QuoteDTO save(long quoteRequestId, CreateQuoteDTO data)
            throws QuoteAlreadyExist, QuoteRequestNotFound, QuoteRequestClosed {
        log.info("data {}", data);
        QuoteRequest quoteRequest = Validation.quoteRequestExists(quoteRequestRepository, quoteRequestId);
        // Validation an existing QuoteRequest is not in CLOSED Status(meaning a Quote
        // of another Supplier have been accepted)
        if (quoteRequest.getStatus() == StatusQuoteRequest.CLOSED) {
            throw new QuoteRequestClosed(quoteRequestId);
        }

        // Validation Quote is not already created for QuoteRequest
        if (quoteRequest.getQuote() != null) {
            throw new QuoteAlreadyExist(quoteRequestId);
        }
        Quote entity = mapper.toModel(data);
        entity.setQuoteRequest(quoteRequest);
        repository.save(entity);

        // Change status of Quote Request to SENT
        quoteRequest.setStatus(StatusQuoteRequest.SENT);
        quoteRequestRepository.save(quoteRequest);

        return mapper.toDTO(entity);

    }

    public QuoteDTO findByQuoteRequest(long quoteRequestId) throws QuoteRequestNotFound, QuoteNotFound {
        QuoteRequest quoteRequest = Validation.quoteRequestExists(quoteRequestRepository, quoteRequestId);
        if (quoteRequest.getQuote() == null) {
            throw new QuoteNotFound(quoteRequestId);
        }
        return mapper.toDTO(quoteRequest.getQuote());
    }

    public void update(long quoteId, ChangeStatusQuoteDTO data) throws QuoteNotFound, CannotChangeQuoteStatus {
        Quote quote = Validation.quoteExists(repository, quoteId);

        // Validation, Client can only change to ACCEPTED status
        if (data.getStatus() != StatusQuote.ACCEPTED) {
            throw new CannotChangeQuoteStatus(quoteId);
        }

        // Validation Quote is not already ACCEPTED OR ASSIGNED
        if (quote.getStatus() == StatusQuote.ACCEPTED || quote.getStatus() == StatusQuote.CLOSED) {
            throw new CannotChangeQuoteStatus(quoteId);
        }

        mapper.update(quote, data);
        repository.save(quote);

        // If the Quote Status is changed to ACCEPTED, all remaining quotes are set to
        // CLOSED
        // and QuoteRequests are set to CLOSED
        ServiceRequest serviceRequest = quote.getQuoteRequest().getServiceRequest();
        List<QuoteRequest> quoteRequests = serviceRequest.getQuoteRequests();

        for (QuoteRequest quoteRequest : quoteRequests) {
            // Change QuoteRequest status to CLOSED
            quoteRequest.setStatus(StatusQuoteRequest.CLOSED);
            quoteRequestRepository.save(quoteRequest);
            // Change Quote status to CLOSED    
            if (quoteRequest.getQuote() != null && quoteRequest.getQuote().getId() != quoteId) {
                Quote quote1 = quoteRequest.getQuote();
                quote1.setStatus(StatusQuote.CLOSED);
                repository.save(quote1);
            }
        }

        //Change ServiceRequest Status to ASSIGNED
        serviceRequest.setStatus(StatusRequest.ASSIGNED);
        serviceRequestRepository.save(serviceRequest);

    }
}
