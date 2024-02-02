package org.bedu.proyecto.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.LinkedList;
import java.util.List;

import org.bedu.proyecto.dto.supplier.CreateSupplierDTO;
import org.bedu.proyecto.dto.supplier.SupplierDTO;
import org.bedu.proyecto.exception.authentication.UnauthorizedAction;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.service.SupplierService;
import org.bedu.proyecto.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SupplierControllerTest {
    @MockBean
    SupplierService service;
    @MockBean
    UserService userService;
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
        assertEquals(supplier.getAddress(), actualSupplierDTOs.get(0).getPhone());
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
        assertEquals(supplierDTO, response);

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
        assertEquals(expectedDto, actualDto);
    
    }


}
