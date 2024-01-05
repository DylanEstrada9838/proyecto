package org.bedu.proyecto.repository;

import java.util.List;

import org.bedu.proyecto.model.Quote;
import org.bedu.proyecto.model.QuoteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findAll();
    Quote findByQuoteRequest(QuoteRequest quoteRequest);
}