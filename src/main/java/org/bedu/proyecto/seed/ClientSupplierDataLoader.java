package org.bedu.proyecto.seed;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.ServiceRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

@Component
@Order(2)
public class ClientSupplierDataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final SupplierRepository supplierRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public ClientSupplierDataLoader(UserRepository userRepository, ClientRepository clientRepository,
            SupplierRepository supplierRepository, ServiceRepository serviceRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.supplierRepository = supplierRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    @Override
    public void run(String... args) {
        List<Long> userIds = List.of(1L, 2L);
        List<String> clientNames = List.of("Dylan", "Dylan2");
        List<String> clientLastNames = List.of("Estrada", "Estrada2");
        List<String> clientPhones = List.of("123456", "1234567");
        List<String> businessNames = List.of("Supplier 1", "Supplier 2");
        List<String> supplierPhones = List.of("123456", "1234567");

        List<List<Long>> serviceIdsList = List.of(
                List.of(1L, 2L), // Services for Supplier 1
                List.of(3L, 4L) // Services for Supplier 2
        );

        int i = 0;
        for (Long userId : userIds) {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                // Create Client
                Client client = new Client();
                client.setName(clientNames.get(i));
                client.setLastName(clientLastNames.get(i));
                client.setPhone(clientPhones.get(i));
                client.setUser(userOptional.get());
                clientRepository.save(client);

                // Create Supplier
                Supplier supplier = new Supplier();
                supplier.setBusinessName(businessNames.get(i));
                supplier.setPhone(supplierPhones.get(i));
                supplier.setUser(userOptional.get());
                supplierRepository.save(supplier);

                // Create Services
                List<Long> serviceIds = serviceIdsList.get(i);
                List<AppService> services = serviceIds.stream()
                        .map(serviceId -> serviceRepository.findById(serviceId).orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                supplier.setServices(services);

                i++; // Increment the index for the next iteration
            }
        }

    }
}