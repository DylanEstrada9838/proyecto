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

import org.junit.jupiter.api.BeforeEach;
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
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.StatusQuoteRequest;
import org.bedu.proyecto.model_enums.StatusSupplierServiceJoin;
import org.bedu.proyecto.repository.SupplierRepository;
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

@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class SupplierControlleE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplierRepository repository;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private QuoteRequestRepository QuoteRequestRepository;

    @Autowired
    private SupplierControllerRepository ControllerRepository;

    private ObjectMapper mapper = new ObjectMapper();
  
    @BeforeEach
    public void setup() {
      repository.deleteAll();
    }

    @Test
    @DisplayName("GET /suppliers should return an empty list")
    void emptyListTest() throws Exception {

        // Realizar una petición de tipo GET
        // hacia /movies y esperar que el resultado sea 200
        MvcResult result = mockMvc.perform(get("/suplliers"))
            .andExpect(status().isOk())
            .andReturn();

    String content = result.getResponse().getContentAsString();

    assertEquals("[]", content);
  }

 
    @Test
    @DisplayName("Controller should return a list of suppliers")
    void findAllTest() throws Exception {
        
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();


        supplier1.setId(99L);
        supplier1.setBusinessName("Plomero Jacinto");
        supplier1.setPhone("25869658");
        supplier1.setAddress("Cabo San Lucas, calle 1");
        supplier1.build();

        supplier2.setId(98L);
        supplier2.setBusinessName("Plomero Miguel");
        supplier2.setPhone("528694");
        supplier2.setAddress("Cabo San Lucas, calle 1");
        supplier2.build();

        repository.save(supplier1);
        repository.save(supplier2);

        MvcResult result = mockMvc.perform(get("/Suppliers"))
        .andExpect(status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();

        TypeReference<List<SupplierDTO>> listTypeReference = new TypeReference<List<SupplierDTO>>() {};

        List<SupplierDTO> response = mapper.readValue(content, listTypeReference);
        
        assertTrue(response.size() == 2);
        assertEquals(supplier1.getId(), response.get(0).getId());
        assertEquals(supplier2.getId(), response.get(1).getId());
        assertEquals(supplier1.getBusinessName(), response.get(0).getBusinessName());
        assertEquals(supplier2.getBusinessName(), response.get(1).getBusinessName());
        assertEquals(supplier1.getPhone(), response.get(0).getBusinessName());
        assertEquals(supplier2.getPhone(), response.get(1).getBusinessName());
        assertEquals(supplier1.getAddress(), response.get(0).getAddress());
        assertEquals(supplier2.getAddress(), response.get(1).getAddress());

    }

    @Test
    @DisplayName("Controller should return a supplier")
    void findByIdTest() throws Exception {
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

