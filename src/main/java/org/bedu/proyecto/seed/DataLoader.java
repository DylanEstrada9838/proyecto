package org.bedu.proyecto.seed;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.ServiceRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, ServiceRepository serviceRepository) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;

    }

    @Transactional
    @Override
    public void run(String... args) {
        Map<String, String> userCredentials = Map.of(
                "dylan@gmail.com", "123",
                "dylan2@gmail.com", "456", "alice@yahoo.com", "p@ssw0rd",
                "bob@hotmail.com", "securePwd",
                "charlie@gmail.com", "letMeIn123",
                "emma@gmail.com", "password123",
                "frank@example.com", "pass1234",
                "grace@gmail.com", "helloWorld",
                "henry@yahoo.com", "qwerty",
                "isabel@hotmail.com", "mySecretPwd");

        userCredentials.forEach((email, password) -> {
            User user = new User();
            user.setPassword(password);
            user.setEmail(email);
            userRepository.save(user);
        });

        List<String> stringList = List.of("Plumbing", "Electrical", "Carpentry", "Painting","HVAC","Cleaning","Pest Control","Roofing","Home Security");

        // Using Stream and map to create instances
        List<AppService> servicesList = stringList.stream()
                .map(name -> new AppService(name))
                .collect(Collectors.toList());

        servicesList.forEach(serviceRepository::save);

    }
}