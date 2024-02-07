// package org.bedu.proyecto.controller;

// import org.bedu.proyecto.repository.ClientRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;

// import com.fasterxml.jackson.databind.ObjectMapper;

// @AutoConfigureTestDatabase(replace = Replace.NONE)
// @AutoConfigureMockMvc
// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// class ClientControllerE2E {
//     @Autowired
//   private MockMvc mockMvc;

//   @Autowired
//   private ClientRepository repository;

//   private ObjectMapper mapper = new ObjectMapper();

//   @BeforeEach
//   public void setup() {
//     repository.deleteAll();
//   }
// }
