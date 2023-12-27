package org.bedu.proyecto.service;

import java.util.List;

import org.bedu.proyecto.dto.quote.ChangeStatusQuoteDTO;
import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.exception.quote.CannotChangeQuoteStatus;
import org.bedu.proyecto.exception.quote.QuoteAlreadyExist;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.exception.quote_request.QuoteRequestNotFound;
import org.bedu.proyecto.mapper.QuoteMapper;
import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model.ServiceRequest;
import org.bedu.proyecto.model_enums.StatusQuote;
import org.bedu.proyecto.model_enums.StatusQuoteRequest;
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
        //Validation Quote is not already created for QuoteRequest
        if (quoteRequest.getQuote() != null) {
            throw new QuoteAlreadyExist(quoteRequestId);
        }
        Quote entity = mapper.toModel(data);
        entity.setQuoteRequest(quoteRequest);
        repository.save(entity);

        //Change status of Quote Request to SENT
        quoteRequest.setStatus(StatusQuoteRequest.SENT);
        quoteRequestRepository.save(quoteRequest);

        return mapper.toDTO(entity);

    }

    public QuoteDTO findByQuoteRequest(long quoteRequestId) throws QuoteRequestNotFound, QuoteNotFound{
        QuoteRequest quoteRequest = Validation.quoteRequestExist(quoteRequestRepository, quoteRequestId);
        if (quoteRequest.getQuote()==null){
            throw new QuoteNotFound(quoteRequestId);
        }
        return mapper.toDTO(quoteRequest.getQuote());
    }

    public void update(long quoteId,ChangeStatusQuoteDTO data) throws QuoteNotFound, CannotChangeQuoteStatus{
        Quote quote = Validation.quoteExist(repository, quoteId);

        //Validation Quote is not already ACCEPTED OR ASSIGNED
        if (quote.getStatus() == StatusQuote.ACCEPTED || quote.getStatus()== StatusQuote.ASSIGNED){
            throw new CannotChangeQuoteStatus(quoteId);
        }
        
        mapper.update(quote,data);
        repository.save(quote);

        //Validation if the Quote Status is changed to ACCEPTED, all remaining quotes are set to ASSIGNED
        if(data.getStatus()==StatusQuote.ACCEPTED){
            QuoteRequest currentQuoteRequest = quote.getQuoteRequest();
            ServiceRequest serviceRequest= quote.getQuoteRequest().getServiceRequest();
            List<QuoteRequest> quoteRequests = serviceRequest.getQuoteRequests();
            if(quoteRequests.size()>1){
                for (QuoteRequest quoteRequest: quoteRequests){
                    if(quoteRequest.getId()!=currentQuoteRequest.getId()){
                        Quote quote1 = quoteRequest.getQuote();
                        quote1.setStatus(StatusQuote.ASSIGNED);
                        repository.save(quote1);    
                    }
                }
            }
            
        }
    }
}
