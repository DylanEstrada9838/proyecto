package org.bedu.proyecto.seed;

import java.util.List;
import java.util.Optional;

import org.bedu.proyecto.model.Address;
import org.bedu.proyecto.model.Client;
import org.bedu.proyecto.model.Supplier;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Gender;
import org.bedu.proyecto.repository.AddressRepository;
import org.bedu.proyecto.repository.ClientRepository;
import org.bedu.proyecto.repository.SupplierRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

@Component
@Profile("!test")
@Order(2)
public class ClientsSuppliersData implements CommandLineRunner {
     @Autowired
     UserRepository userRepository;
     @Autowired
     ClientRepository clientRepository;
     @Autowired
     SupplierRepository supplierRepository;
     @Autowired
     AddressRepository addressRepository;

    @Transactional
    @Override
    public void run(String... args) {
        List<Long> userIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        // Client data
        List<String> clientNames = List.of("Dylan", "Ivy", "Alice", "Bob", "Charlie", "Emma", "Frank", "Grace", "Henry",
                "Isabel");
        List<String> clientLastNames = List.of("Estrada", "Moore", "Smith", "Johnson", "Williams", "Jones", "Brown",
                "Davis", "Miller", "Wilson");
        List<String> clientPhones = List.of("123-456-7890", "234-567-8901", "345-678-9012", "456-789-0123",
                "567-890-1234", "678-901-2345", "789-012-3456", "890-123-4567", "901-234-5678", "012-345-6789");
        List<Integer> clientAges = List.of(25, 25, 30, 35, 40, 22, 28, 31, 27, 29);
        List<Gender> clientGenders = List.of(Gender.MALE, Gender.FEMALE, Gender.FEMALE, Gender.MALE, Gender.MALE,
                Gender.FEMALE, Gender.MALE, Gender.FEMALE, Gender.MALE, Gender.FEMALE);
        // Address data
        List<String> addressLine_1 = List.of("123 Main St", "456 Oak Ave", "789 Pine Rd", "101 Maple Blvd",
                "202 Cedar Ln", "303 Elm St", "404 Birch Ave", "505 Spruce Rd", "606 Pine Ln", "707 Oak Blvd");
        List<String> addressLine_2 = List.of("Apt 101", "Suite 202", "Unit 303", "Apt 404", "Suite 505", "Unit 606",
                "Apt 707", "Suite 808", "Unit 909", "Apt 1010");
        List<String> addressCity = List.of("CityA", "CityB", "CityC", "CityD", "CityE", "CityF", "CityG", "CityH",
                "CityI", "CityJ");
        List<String> addressState = List.of("CA", "NY", "TX", "FL", "IL", "PA", "OH", "GA", "NC", "MI");
        List<String> addressPostalCode = List.of("12345", "67890", "34567", "89012", "45678", "90123", "56789", "23456",
                "78901", "01234");
        // Supplier Data
        List<String> businessNames = List.of("Supplier 1", "Supplier 2", "Supplier 3", "Supplier 4", "Supplier 5",
                "Supplier 6", "Supplier 7", "Supplier 8", "Supplier 9", "Supplier 10");
        List<String> supplierPhones = List.of("123-456-7890", "234-567-8901", "345-678-9012", "456-789-0123",
                "567-890-1234", "678-901-2345", "789-012-3456", "890-123-4567", "901-234-5678", "012-345-6789");
        List<String> supplierAddresses = List.of(
                "123 Main Street Citytown, ST 54321", "456 Elm Avenue Villageland, AB 98765",
                "789 Oak Lane Hamletville, XY 12345", "101 Pine Road Metropolis, ZW 67890",
                "202 Maple Street Townsville, PQ 13579", "303 Cedar Avenue Villageburg, KL 24680",
                "404 Birch Lane Hamletown, UV 97531", "505 Redwood Road Cityville, RS 86420",
                "606 Spruce Lane Countryside, WX 75309", "707 Pine Road Mountainview, NM 36912");

        int i = 0;
        for (Long userId : userIds) {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                // Create Client
                Client client = Client.builder()
                        .name(clientNames.get(i))
                        .lastName(clientLastNames.get(i))
                        .phone(clientPhones.get(i))
                        .age(clientAges.get(i))
                        .gender(clientGenders.get(i))
                        .user(userOptional.get())
                        // set other fields if needed
                        .build();
                clientRepository.save(client);
                // Create Address and set Client
                Address address = Address.builder()
                        .line_1(addressLine_1.get(i))
                        .line_2(addressLine_2.get(i))
                        .city(addressCity.get(i))
                        .state(addressState.get(i))
                        .postalCode(addressPostalCode.get(i))
                        .client(client)
                        .build();
                addressRepository.save(address);
                // Create Supplier
                Supplier supplier = Supplier.builder().businessName(businessNames.get(i)).phone(supplierPhones.get(i))
                        .address(supplierAddresses.get(i)).user(userOptional.get()).build();
                supplierRepository.save(supplier);
            }

            i++; // Increment the index for the next iteration
        }
    }
}
