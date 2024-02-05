package org.bedu.proyecto.seed;

import java.util.Arrays;
import java.util.List;

import org.bedu.proyecto.dto.servicerequest.CreateServiceRequestDTO;
import org.bedu.proyecto.exception.address.AddressNotAssignedToClient;
import org.bedu.proyecto.exception.address.AddressNotFound;
import org.bedu.proyecto.exception.client.ClientNotFoundException;
import org.bedu.proyecto.exception.request.ServiceRequestCreateNotAllowed;
import org.bedu.proyecto.exception.service.ServiceNotFoundException;
import org.bedu.proyecto.exception.supplier.ServiceNotAssignedException;
import org.bedu.proyecto.model_enums.Urgency;
import org.bedu.proyecto.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
// @Profile("!test")
@Order(4)
public class ServiceRequestsData implements CommandLineRunner {
    @Autowired
    ServiceRequestService service;

    @Transactional
    @Override
    public void run(String... args) throws ClientNotFoundException, ServiceNotFoundException,
            ServiceNotAssignedException, ServiceRequestCreateNotAllowed, AddressNotAssignedToClient, AddressNotFound {
        List<String> serviceDescriptions = Arrays.asList(
                "Fixing plumbing issues",
                "Handling electrical installations",
                "Providing carpentry services",
                "Painting interior and exterior",
                "Heating, ventilation, and air conditioning services",
                "Cleaning residential and commercial spaces",
                "Pest control and extermination",
                "Roofing repair and maintenance",
                "Various home security solutions");

        List<Long> serviceIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        List<Long> clientsIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        List<Long> addressIds = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        List<Urgency> urgencies = Arrays.asList(
                Urgency.LOW, Urgency.HIGH, Urgency.MEDIUM,
                Urgency.HIGH, Urgency.LOW, Urgency.MEDIUM,
                Urgency.MEDIUM, Urgency.LOW, Urgency.HIGH);
        int i = 0;
        for (Long serviceId : serviceIds) {
            try {
                CreateServiceRequestDTO dto = new CreateServiceRequestDTO(
                        serviceDescriptions.get(i),
                        addressIds.get(i),
                        urgencies.get(i),
                        serviceId);
                service.save(clientsIds.get(i), dto);
            } catch (Exception e) {
                // Handle the exception appropriately, log or rethrow if necessary
                e.printStackTrace();
            }
            i++;
        }
    }
}
