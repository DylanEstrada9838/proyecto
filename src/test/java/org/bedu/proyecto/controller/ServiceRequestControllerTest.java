package org.bedu.proyecto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.bedu.proyecto.dto.quote_request.CreateQuoteRequestDTO;
import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.dto.servicerequest.ServiceRequestDTO;
import org.bedu.proyecto.dto.servicerequest.UpdateServiceRequestDTO;
import org.bedu.proyecto.exception.quote_request.QuoteRequestAcceptedExist;
import org.bedu.proyecto.exception.quote_request.QuoteRequestAlreadyExist;
import org.bedu.proyecto.exception.request.RequestSameUserNotAllowed;
import org.bedu.proyecto.exception.request.ServiceRequestNotFound;
import org.bedu.proyecto.exception.request.UpdateServiceRequestNotAllowed;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierServiceNotActive;
import org.bedu.proyecto.model_enums.StatusQuoteRequest;
import org.bedu.proyecto.model_enums.StatusRequest;
import org.bedu.proyecto.model_enums.Urgency;
import org.bedu.proyecto.service.QuoteRequestService;
import org.bedu.proyecto.service.ServiceRequestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ServiceRequestControllerTest {
    @MockBean
    ServiceRequestService service;
    @MockBean
    QuoteRequestService quoteRequestService;
    @Autowired
    ServiceRequestController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Controller should return a service request.")
    void findByIdTest() throws ServiceRequestNotFound {
        ServiceRequestDTO dto = ServiceRequestDTO.builder()
                .addressId(99L)
                .clientId(99L)
                .createdAt(Instant.now())
                .description("casa")
                .id(99)
                .status(StatusRequest.OPEN)
                .urgency(Urgency.HIGH)
                .serviceId(99L)
                .build();

        when(service.findById(anyLong())).thenReturn(dto);
        ServiceRequestDTO result = controller.findById(99L);
        assertNotNull(result);
        assertEquals(dto.getAddressId(), result.getAddressId());
        assertEquals(dto.getClientId(), result.getClientId());
        assertEquals(dto.getCreatedAt(), result.getCreatedAt());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getStatus(), result.getStatus());
        assertEquals(dto.getUrgency(), result.getUrgency());
        assertEquals(dto.getServiceId(), result.getServiceId());

    }
        @Test
    @DisplayName("Controller should save a Quote Request")
    void saveTest() throws ServiceRequestNotFound, SupplierNotFoundException, RequestSameUserNotAllowed,
            ServiceNotAssignedException, QuoteRequestAlreadyExist, QuoteRequestAcceptedExist, SupplierServiceNotActive {
        CreateQuoteRequestDTO createDTO = CreateQuoteRequestDTO.builder()
                .supplierId(99L)
                .build();

        QuoteRequestDTO expectedDto = QuoteRequestDTO.builder()
                .supplierId(99L)
                .status(StatusQuoteRequest.PENDING)
                .build();

        when(quoteRequestService.save(anyLong(), any(CreateQuoteRequestDTO.class))).thenReturn(expectedDto);
        // Act
        QuoteRequestDTO actualDto = controller.addQuoteRequest(99L, createDTO);
        // Assert
        assertNotNull(actualDto);
        assertEquals(expectedDto.getSupplierId(), actualDto.getSupplierId());
        assertEquals(expectedDto.getStatus(), actualDto.getStatus());

    }

    @Test
    @DisplayName("Controller should find a list of Quote Request")
    void findAllQuoteRequestByServiceRequestTest() throws ServiceRequestNotFound {
        List<QuoteRequestDTO> expectedQuoteRequestsDTOs = new LinkedList<>();

        QuoteRequestDTO request = QuoteRequestDTO.builder()
                .id(99L)
                .status(StatusQuoteRequest.PENDING)
                .serviceRequestId(99L)
                .supplierId(99L)
                .createdAt(Instant.now())
                .build();
        expectedQuoteRequestsDTOs.add(request);

        when(quoteRequestService.findAllByServiceRequest(anyLong())).thenReturn(expectedQuoteRequestsDTOs);

        // Act
        List<QuoteRequestDTO> actualQuoteRequestsDTOs = controller.findAllQuoteRequestByServiceRequest(99L);
        // Assert
        assertEquals(expectedQuoteRequestsDTOs, actualQuoteRequestsDTOs);
        assertTrue(actualQuoteRequestsDTOs.size() > 0);
        assertEquals(request.getId(), actualQuoteRequestsDTOs.get(0).getId());
        assertEquals(request.getStatus(), actualQuoteRequestsDTOs.get(0).getStatus());
        assertEquals(request.getServiceRequestId(), actualQuoteRequestsDTOs.get(0).getServiceRequestId());
        assertEquals(request.getSupplierId(), actualQuoteRequestsDTOs.get(0).getSupplierId());
        assertEquals(request.getCreatedAt(), actualQuoteRequestsDTOs.get(0).getCreatedAt());

    }
    @Test
@DisplayName("Controller should update a Service Request")
void updateTest() throws ServiceRequestNotFound, UpdateServiceRequestNotAllowed{

UpdateServiceRequestDTO dto = UpdateServiceRequestDTO.builder()
.description("Already written")
.addressId(999L)
.urgency(Urgency.HIGH)
.serviceId(99L)
.build();

controller.update(999L, dto);
verify(service,times(1)).update(999L, dto);
}

}
