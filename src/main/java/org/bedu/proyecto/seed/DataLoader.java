package org.bedu.proyecto.seed;

import java.util.List;

import java.util.stream.Collectors;

import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.repository.ServiceRepository;

import org.bedu.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, ServiceRepository serviceRepository) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;

    }

    @Override
    public void run(String... args) {
        User user = new User();
        user.setPassword("123");
        user.setEmail("dylan@gmail.com");
        userRepository.save(user);

        List<String> stringList = List.of("Plumbing", "Electrical", "Carpentry", "Painting");

        // Using Stream and map to create instances of Fruit
        List<AppService> servicesList = stringList.stream()
                .map(name -> new AppService(name))
                .collect(Collectors.toList());

        // Save each Fruit instance to the database
        servicesList.forEach(serviceRepository::save);

    }
}
