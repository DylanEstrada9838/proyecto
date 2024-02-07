package org.bedu.proyecto.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.bedu.proyecto.dto.quote.CreateQuoteDTO;
import org.bedu.proyecto.dto.quote.QuoteDTO;
import org.bedu.proyecto.exception.quote.QuoteAlreadyExist;
import org.bedu.proyecto.exception.quote.QuoteNotFound;
import org.bedu.proyecto.exception.quote_request.QuoteRequestClosed;
import org.bedu.proyecto.exception.quote_request.QuoteRequestNotFound;
import org.bedu.proyecto.model.QuoteRequest;
import org.bedu.proyecto.model_enums.StatusQuote;
import org.bedu.proyecto.model_enums.StatusQuoteRequest;
import org.bedu.proyecto.repository.QuoteRequestRepository;
import org.bedu.proyecto.service.QuoteRequestService;
import org.bedu.proyecto.service.QuoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
 class QuoteRequestControllerTest {
    @MockBean
    QuoteRequestService service;
    @MockBean
    QuoteService quoteService;
    @MockBean
    QuoteRequestRepository quoteRequestRepository;
    @Autowired
    QuoteRequestController controller;

    @Test
    @DisplayName("Controller should be injected")
     void smokeTest(){
        assertNotNull(controller);
    }
   @Test
    @DisplayName("Controller should add quote")
    void addQuoteTest() throws QuoteAlreadyExist, QuoteRequestNotFound, QuoteRequestClosed{
        CreateQuoteDTO createQuoteDTO = CreateQuoteDTO.builder()
        .totalCost(BigDecimal.valueOf(55))
        .build();

        QuoteRequest quoteRequest= QuoteRequest.builder()
                .id(99L)
                .status(StatusQuoteRequest.PENDING)
                .createdAt(Instant.now())
                .build();

        QuoteDTO dto =  QuoteDTO.builder()
        .id(99L)
        .totalCost(BigDecimal.valueOf(55))
        .status(StatusQuote.PENDING)
        .quoteRequestId(99L)
        .createdAt(Instant.now())
        .build();
        when(quoteService.save(anyLong(), any(CreateQuoteDTO.class))).thenReturn(dto);
        when(quoteRequestRepository.findById(anyLong())).thenReturn(Optional.of(quoteRequest));
        QuoteDTO result = controller.addQuote(99L, createQuoteDTO);
        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getTotalCost(), result.getTotalCost());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getQuoteRequestId(), result.getQuoteRequestId());
        assertEquals(dto.getCreatedAt(), result.getCreatedAt());
    }
@Test
    @DisplayName("Controller should find quote")
    void findQuoteTest() throws QuoteRequestNotFound, QuoteNotFound{

        QuoteRequest quoteRequest= QuoteRequest.builder()
                .id(99L)
                .status(StatusQuoteRequest.PENDING)
                .createdAt(Instant.now())
                .build();

        QuoteDTO dto =  QuoteDTO.builder()
        .id(99L)
        .totalCost(BigDecimal.valueOf(55))
        .status(StatusQuote.PENDING)
        .quoteRequestId(99L)
        .createdAt(Instant.now())
        .build();
        when(quoteRequestRepository.findById(anyLong())).thenReturn(Optional.of(quoteRequest));
        when(quoteService.findByQuoteRequest(anyLong())).thenReturn(dto);

        QuoteDTO result = controller.findQuote(99L);
        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getTotalCost(), result.getTotalCost());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getQuoteRequestId(), result.getQuoteRequestId());
        assertEquals(dto.getCreatedAt(), result.getCreatedAt());
    }
    
}
