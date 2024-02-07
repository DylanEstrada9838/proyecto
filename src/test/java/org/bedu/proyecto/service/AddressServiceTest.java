package org.bedu.proyecto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.dto.address.CreateAddressDTO;
import org.bedu.proyecto.dto.address.UpdateAddressDTO;
import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.address.UpdateOrDeleteNotAllowed;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.dto.address.AddressDTO;
import org.bedu.proyecto.model.Address;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.repository.AddressRepository;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.ServiceRequestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AddressServiceTest {
    @Autowired
    AddressService service;

    @MockBean
    AddressRepository repository;

    @MockBean
    ServiceRequestRepository serviceRequestRepository;
    @MockBean
    ClientRepository clientRepository;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Service should save an address")
    void saveTest() throws ClientNotFoundException {
        CreateAddressDTO createDTO = CreateAddressDTO.builder()
                .city("city A")
                .line1("streetA")
                .postalCode("12345")
                .state("state A")
                .build();

        Address address = Address.builder()
                .city("city A")
                .line1("streetA")
                .postalCode("12345")
                .state("state A")
                .build();
        Client client = Client.builder()
                .id(99L)
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build();
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(repository.save(any(Address.class))).thenReturn(address);

        AddressDTO addressDTO = service.save(999L, createDTO);

        assertNotNull(addressDTO);
        assertEquals(address.getId(), addressDTO.getId());
        assertEquals(address.getCity(), addressDTO.getCity());
        assertEquals(address.getLine1(), addressDTO.getLine1());
        assertEquals(address.getPostalCode(), addressDTO.getPostalCode());
        assertEquals(address.getState(), addressDTO.getState());

    }

    @Test
    @DisplayName("Service should update an address")
    void updateTest() throws AddressNotFound, UpdateOrDeleteNotAllowed {
        UpdateAddressDTO addressDTO = UpdateAddressDTO.builder()
                .city("city A")
                .line1("streetA")
                .postalCode("12345")
                .state("state A")
                .build();
        Address address = Address.builder()
                .id(99L)
                .city("city B")
                .line1("street B")
                .postalCode("12345678")
                .state("state B")
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(address));
        when(serviceRequestRepository.findAllByAddress(anyLong())).thenReturn(Collections.emptyList());

        service.update(99L, addressDTO);

        assertEquals(address.getCity(), addressDTO.getCity());
        assertEquals(address.getLine1(), addressDTO.getLine1());
        assertEquals(address.getPostalCode(), addressDTO.getPostalCode());
        assertEquals(address.getState(), addressDTO.getState());
        verify(repository, times(1)).save(address);
    }

    @Test
    @DisplayName("Service should delete a address")
    void deleteTest() throws AddressNotFound, UpdateOrDeleteNotAllowed {
        Address address = Address.builder()
                .id(99L)
                .city("city B")
                .line1("street B")
                .postalCode("12345678")
                .state("state B")
                .build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(address));
        when(repository.findById(anyLong())).thenReturn(Optional.of(address));
        service.delete(99L);
        verify(repository, times(1)).delete(address);

    }

    @Test
    @DisplayName("Service should return a list of addresses")
    void findAllByClientTest() throws ClientNotFoundException {
        List<Address> addresses = new LinkedList<>();

        Address address = Address.builder()
                .id(99L)
                .city("city B")
                .line1("street B")
                .postalCode("12345678")
                .state("state B")
                .build();
        Client client = Client.builder()
                .id(99L)
                .name("test")
                .lastName("test")
                .age(30)
                .gender(Gender.MALE)
                .phone("12345678")
                .build();

        addresses.add(address);
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(repository.findAllByClient(client)).thenReturn(addresses);
        List <AddressDTO> addressDTOs = service.findAllByClient(99L);
        assertTrue(addressDTOs.size()>0);
        assertEquals(address.getId(), addressDTOs.get(0).getId());
        assertEquals(address.getCity(), addressDTOs.get(0).getCity());
        assertEquals(address.getLine1(), addressDTOs.get(0).getLine1());
        assertEquals(address.getPostalCode(), addressDTOs.get(0).getPostalCode());
        assertEquals(address.getState(), addressDTOs.get(0).getState());
    }

}
