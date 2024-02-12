package org.bedu.proyecto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.supplier.CreateSupplierDTO;
import org.bedu.proyecto.dto.supplier.SupplierDTO;
import org.bedu.proyecto.dto.supplier.UpdateSupplierDTO;
import org.bedu.proyecto.exception.supplier.SupplierNotFoundException;
import org.bedu.proyecto.exception.supplier.SupplierUserAlreadyExist;
import org.bedu.proyecto.exception.user.UserNotFoundException;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.SupplierServiceJoinRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SupplierServiceTest {
    @MockBean
    SupplierRepository repository;

    @MockBean
    SupplierServiceJoinRepository supplierServiceJoinRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    SupplierService service;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Service should return a list of suppliers")
    void findAllTest() {
        List<Supplier> suppliers = new LinkedList<>();
        Supplier supplier = Supplier.builder()
                .id(999)
                .businessName("Business 999")
                .phone("121212")
                .address("city A street B")
                .build();

        suppliers.add(supplier);

        when(repository.findAll()).thenReturn(suppliers);

        List<SupplierDTO> supplierDTOs = service.findAll();
        assertNotNull(supplierDTOs);
        assertTrue(supplierDTOs.size() > 0);
        assertEquals(supplier.getId(), supplierDTOs.get(0).getId());
        assertEquals(supplier.getBusinessName(), supplierDTOs.get(0).getBusinessName());
        assertEquals(supplier.getAddress(), supplierDTOs.get(0).getAddress());
        assertEquals(supplier.getPhone(), supplierDTOs.get(0).getPhone());

    }

    @Test
    @DisplayName("Service should return a supplier")
    void findByIdTest() throws SupplierNotFoundException {
        Supplier supplier = Supplier.builder()
                .id(999)
                .businessName("Business 999")
                .phone("121212")
                .address("city A street B")
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(supplier));

        SupplierDTO supplierDTO = service.findById(99L);

        assertNotNull(supplierDTO);
        assertEquals(supplier.getId(), supplierDTO.getId());
        assertEquals(supplier.getBusinessName(), supplierDTO.getBusinessName());
        assertEquals(supplier.getAddress(), supplierDTO.getAddress());
        assertEquals(supplier.getPhone(), supplierDTO.getPhone());
    }

    @Test
    @DisplayName("Service should save a supplier")
    void saveTest() throws UserNotFoundException, SupplierUserAlreadyExist {
        CreateSupplierDTO createDTO = CreateSupplierDTO.builder()
                .businessName("Business 999")
                .phone("121212")
                .address("city A street B")
                .build();
        Supplier supplier = Supplier.builder()
                .businessName("Business 999")
                .phone("121212")
                .address("city A street B")
                .build();
        User user = User.builder()
                .id(999L)
                .email("test@mail.com")
                .password("123")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(repository.save(any(Supplier.class))).thenReturn(supplier);

        SupplierDTO dto = service.save(createDTO, 999L);

        assertNotNull(dto);
        assertEquals(supplier.getId(), dto.getId());
        assertEquals(supplier.getBusinessName(), dto.getBusinessName());
        assertEquals(supplier.getAddress(), dto.getAddress());
        assertEquals(supplier.getPhone(), dto.getPhone());
    }

    @Test
    @DisplayName("Service should update a supplier")
    void updateTest() throws SupplierNotFoundException {
        UpdateSupplierDTO dto = UpdateSupplierDTO.builder()
                .businessName("Business 999")
                .phone("121212")
                .address("city A street A")
                .build();

        Supplier supplier = Supplier.builder()
                .id(99L)
                .businessName("Business 333")
                .phone("131313")
                .address("city B street B")
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(supplier));

        service.update(99L, dto);

        assertEquals(dto.getBusinessName(), supplier.getBusinessName());
        assertEquals(dto.getAddress(), supplier.getAddress());
        assertEquals(dto.getPhone(), supplier.getPhone());
        verify(repository, times(1)).save(supplier);
    }

    @Test
    @DisplayName("Service should delete a supplier")
    void deleteTest() throws SupplierNotFoundException {
        Supplier supplier = Supplier.builder()
                .id(99L)
                .businessName("Business 333")
                .phone("131313")
                .address("city B street B")
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(supplier));
        service.delete(99L);
        verify(repository, times(1)).delete(supplier);

    }

}
