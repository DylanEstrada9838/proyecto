package org.bedu.proyecto.seed;

import java.util.List;

import org.bedu.proyecto.model.AppService;
import org.bedu.proyecto.model.User;
import org.bedu.proyecto.model_enums.Role;
import org.bedu.proyecto.repository.ServiceRepository;
import org.bedu.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
@Profile("!test")
@Order(1)
public class UsersServicesData implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    PasswordEncoder encoder;

    @Transactional
    @Override
    public void run(String... args) {

                List<String> emails = List.of(
                "dylan@gmail.com",
                "ivy@gmail.com",  
                "alice@yahoo.com",
                "bob@hotmail.com", 
                "charlie@gmail.com", 
                "emma@gmail.com", 
                "frank@example.com", 
                "grace@gmail.com", 
                "henry@yahoo.com",
                "isabel@hotmail.com");
                List<String> passwords = List.of( 
                "123",
                "456", 
                 "p@ssw0rd",
                 "securePwd",
                 "letMeIn123",
                "password123",
                 "pass1234",
                 "helloWorld",
                 "qwerty",
                 "mySecretPwd");

        int i = 0;
        for (String email:emails) {
            User user = User.builder().email(email).password(encoder.encode(passwords.get(i))).role(Role.ROLE_USER).build();
            userRepository.save(user);
            i++;
        };
        User admin = User.builder().email("admin@admin").password(encoder.encode("123")).role(Role.ROLE_ADMIN).build();
                 userRepository.save(admin);
        //Services data
        List<String> stringList = List.of("Plumbing", "Electrical", "Carpentry", "Painting","HVAC","Cleaning","Pest Control","Roofing","Home Security");

        // Using Stream and map to create instances
        List<AppService> servicesList = stringList.stream()
                .map(name -> AppService.builder().build())
                .toList();

        servicesList.forEach(serviceRepository::save);

    }
}