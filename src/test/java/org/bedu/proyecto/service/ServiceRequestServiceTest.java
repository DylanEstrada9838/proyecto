package org.bedu.proyecto.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.bedu.proyecto.mapper.ServiceRequestMapper;
import org.bedu.proyecto.repository.AddressRepository;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.ServiceRepository;
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
class ServiceRequestServiceTest {
    @MockBean
    private ServiceRequestRepository repository;
    @MockBean
    private ServiceRequestMapper mapper;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private ServiceRepository serviceRepository;
    @MockBean
    private AddressRepository addressRepository;
    @Autowired
    ServiceRequestService service;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

}
