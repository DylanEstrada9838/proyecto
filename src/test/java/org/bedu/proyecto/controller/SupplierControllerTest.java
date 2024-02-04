package org.bedu.proyecto.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.bedu.proyecto.dto.quote_request.QuoteRequestDTO;
import org.bedu.proyecto.dto.service.AddServiceDTO;
import org.bedu.proyecto.dto.service.RemoveServiceDTO;
import org.bedu.proyecto.dto.supplier.CreateSupplierDTO;
import org.bedu.proyecto.dto.supplier.SupplierDTO;
import org.bedu.proyecto.dto.supplier.UpdateSupplierDTO;
import org.bedu.proyecto.dto.supplier_service.ChangeStatusDTO;
import org.bedu.proyecto.dto.supplier_service.ServicesBySupplierDTO;
import org.bedu.proyecto.dto.supplier_service.SuppliersByServicesDTO;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceAlreadyAssignedException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.model_enums.StatusQuoteRequest;
import org.bedu.proyecto.model_enums.StatusSupplierServiceJoin;
import org.bedu.proyecto.service.QuoteRequestService;
import org.bedu.proyecto.service.SupplierService;
import org.bedu.proyecto.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class SupplierControllerTest {
    @MockBean
    SupplierService service;
    @MockBean
    UserService userService;
    @MockBean
    QuoteRequestService quoteRequestService;
    @Autowired
    SupplierController controller;

    @Test
    @DisplayName("Controller should be injected")
    void smokeTest(){
        assertNotNull(controller);
    }
     @Test
    @DisplayName("Controller should return a list of suppliers")
    void findAllTest() {
        List<SupplierDTO> expectedSupplierDTOs = new LinkedList<>();

        SupplierDTO supplier = SupplierDTO.builder()
                .id(999)
                .businessName("Business 999")
                .phone("121212")
                .address("city A street B")
                .build();
        expectedSupplierDTOs.add(supplier);

        when(service.findAll()).thenReturn(expectedSupplierDTOs);

        //Act
        List<SupplierDTO> actualSupplierDTOs = controller.findAll();
        //Assert
        assertEquals(expectedSupplierDTOs, actualSupplierDTOs);
        assertTrue(actualSupplierDTOs.size()>0);
        assertEquals(supplier.getBusinessName(), actualSupplierDTOs.get(0).getBusinessName());
        assertEquals(supplier.getAddress(), actualSupplierDTOs.get(0).getAddress());
        assertEquals(supplier.getPhone(), actualSupplierDTOs.get(0).getPhone());


    }
    @Test
    @DisplayName("Controller should return a supplier")
    void findByIdTest() throws SupplierNotFoundException, UnauthorizedAction {
        // Mocking behavior of service.findById()
        SupplierDTO supplierDTO = SupplierDTO.builder()
        .id(1L)
        .businessName("Business 999")
        .phone("121212")
        .address("city A street B")
        .build();
        when(service.findById(1L)).thenReturn(supplierDTO);

        // Call the controller method
        SupplierDTO response = controller.findById(1L);
        assertEquals(supplierDTO.getBusinessName(), response.getBusinessName());
        assertEquals(supplierDTO.getPhone(), response.getPhone());
        assertEquals(supplierDTO.getAddress(), response.getAddress());


        // Verify that the service methods were called
        verify(service).findById(1L);

    }
    @Test
    @DisplayName("Controller should save a supplier")
    void saveTest() throws UserNotFoundException, SupplierUserAlreadyExist{
        //Arrange
        CreateSupplierDTO createDTO = CreateSupplierDTO.builder()
        .businessName("Business 999")
        .phone("121212")
        .address("city A street B")
        .build();

        SupplierDTO expectedDto = SupplierDTO.builder()
        .id(999)
        .businessName("Business 999")
        .phone("121212")
        .address("city A street B")
        .build();

        when(service.save(any(CreateSupplierDTO.class), anyLong())).thenReturn(expectedDto);
        //Act
        SupplierDTO actualDto = controller.save(createDTO);
        //Assert
        assertNotNull(actualDto);
        assertEquals(expectedDto.getBusinessName(), actualDto.getBusinessName());
        assertEquals(expectedDto.getAddress(), actualDto.getAddress());
        assertEquals(expectedDto.getPhone(), actualDto.getPhone());

    
    }
    @Test
    @DisplayName("Controller should update a Supplier")
    void updateTest() throws SupplierNotFoundException, UnauthorizedAction, UserNotFoundException{
        when(userService.retrieveUserId()).thenReturn(999L);

        UpdateSupplierDTO dto = UpdateSupplierDTO.builder()
        .address("street A City B")
        .businessName("Business 999")
        .phone("999 999 999")
        .build();
        controller.update(999L, dto);
        verify(service,times(1)).update(999L, dto);
    }

    @Test
    @DisplayName("Controller should delete a Supplier")
    void deleteTest () throws SupplierNotFoundException, UnauthorizedAction, UserNotFoundException{
        when(userService.retrieveUserId()).thenReturn(999L);
        
        controller.delete(999L);

        verify(service,times(1)).delete(999L);
    
    }
    @Test
    @DisplayName("Controller should call addService once")
    void addServiceTest() throws SupplierNotFoundException, ServiceNotFoundException, ServiceAlreadyAssignedException{
        AddServiceDTO dto = AddServiceDTO.builder()
        .serviceId(9L)
        .yearsExperience(9)
        .build();

        controller.addService(9L, dto);
        verify(service,times(1)).addService(9L, dto);
    }

    @Test
    @DisplayName("Controller should call the removeService once")
    void removeServiceTest() throws SupplierNotFoundException, ServiceNotFoundException, ServiceNotAssignedException{
        RemoveServiceDTO dto = RemoveServiceDTO.builder()
        .serviceId(9L).build();
        controller.removeService(9L, dto);
        verify(service,times(1)).removeService(9L,dto.getServiceId());
    }

    @Test
    @DisplayName("Controller should update the SupplierServiceJoin status and call the service once")
    void updateServiceTest() throws SupplierNotFoundException, ServiceNotFoundException, ServiceNotAssignedException{
        ChangeStatusDTO dto = ChangeStatusDTO.builder()
        .status(StatusSupplierServiceJoin.INACTIVE).build();

        controller.updateService(999L, 999L, dto);
        verify(service,times(1)).changeServiceStatus(dto, 999L, 999L);

    }

    @Test
    @DisplayName("Controller should return a list of ServicesBySupplierDTO")
    void findAllServicesBySuppliersTest() throws SupplierNotFoundException{
        List<ServicesBySupplierDTO> list = new LinkedList<>();

        ServicesBySupplierDTO dto = ServicesBySupplierDTO.builder()
        .service("Carpentry")
        .yearsExperience(99)
        .averageRating(new BigDecimal(4.90))
        .countRating(99)
        .build();
        list.add(dto);

        when(service.findAllBySupplier(99L)).thenReturn(list);

        List<ServicesBySupplierDTO> result = controller.findAllServicesBySupplier(99L);

        assertNotNull(result);
        assertTrue(result.size()>0);
        assertEquals(dto.getService(), result.get(0).getService());
        assertEquals(dto.getYearsExperience(), result.get(0).getYearsExperience());
        assertEquals(dto.getCountRating(), result.get(0).getCountRating());
        assertEquals(dto.getAverageRating(), result.get(0).getAverageRating());


    }
    @Test
    @DisplayName("Controller should return a list of ServicesBySupplierDTO")
    void findAllSuppliersByServiceTest() throws ServiceNotFoundException{
        List<SuppliersByServicesDTO> list = new LinkedList<>();

        SuppliersByServicesDTO dto = SuppliersByServicesDTO.builder()
        .supplier("Supplier 999")
        .yearsExperience(99)
        .averageRating(new BigDecimal(4.8))
        .countRating(99)
        .build();
        list.add(dto);

        when(service.findAllByService(99L)).thenReturn(list);

        List<SuppliersByServicesDTO> result = controller.findAllSuppliersByService(99L);

        assertNotNull(result);
        assertTrue(result.size()>0);
        assertEquals(dto.getSupplier(), result.get(0).getSupplier());
        assertEquals(dto.getYearsExperience(), result.get(0).getYearsExperience());
        assertEquals(dto.getAverageRating(), result.get(0).getAverageRating());
        assertEquals(dto.getCountRating(), result.get(0).getCountRating());

    }

    @Test
    @DisplayName("Controller should return a list of QuoteRequests")
    void findAllQuoteRequestBySupplier() throws SupplierNotFoundException{
        List<QuoteRequestDTO> list = new LinkedList<>();

        QuoteRequestDTO dto = QuoteRequestDTO.builder()
        .id(99L)
        .status(StatusQuoteRequest.PENDING)
        .serviceRequestId(99L)
        .supplierId(99L)
        .createdAt(Instant.now()).
        build();

        list.add(dto);

        when(quoteRequestService.findAllBySupplier(99L)).thenReturn(list);

        List<QuoteRequestDTO> result = controller.findAllQuoteRequestBySupplier(99L);

        assertNotNull(result);
        assertTrue(result.size()>0);
        assertEquals(dto.getId(), result.get(0).getId());
        assertEquals(dto.getServiceRequestId(), result.get(0).getServiceRequestId());
        assertEquals(dto.getStatus(), result.get(0).getStatus());
        assertEquals(dto.getSupplierId(), result.get(0).getSupplierId());
        assertEquals(dto.getCreatedAt(), result.get(0).getCreatedAt());
    }



   


}
