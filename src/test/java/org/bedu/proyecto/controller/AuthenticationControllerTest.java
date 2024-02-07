// package org.bedu.proyecto.controller;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.Base64;

// import org.bedu.proyecto.dto.appointment.ChangeStatusAppointmentDTO;
// import org.bedu.proyecto.dto.authentication.AuthenticationRequest;
// import org.bedu.proyecto.dto.authentication.AuthenticationResponse;
// import org.bedu.proyecto.dto.authentication.RegisterRequest;
// import org.bedu.proyecto.exception.appointment.AppointmentAlreadyCompleted;
// import org.bedu.proyecto.exception.appointment.AppointmentNotFound;
// import org.bedu.proyecto.exception.appointment.CannotChangeStatus;
// import org.bedu.proyecto.model_enums.StatusAppointment;
// import org.bedu.proyecto.service.AppointmentService;
// import org.bedu.proyecto.service.AuthenticationService;
// import org.bedu.proyecto.service.JwtService;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.ActiveProfiles;

// import io.jsonwebtoken.Claims;



// @ExtendWith(MockitoExtension.class)
// @ActiveProfiles("test")
// @SpringBootTest
// class AuthenticationControllerTest {
//     @MockBean
//     AuthenticationService service;
//     @Autowired
//     JwtService jwtService;
//     @Autowired
//     AuthenticationController controller;

//     @Test
//     @DisplayName("Controller should be injected")
//     void smokeTest() {
//         assertNotNull(controller);
//     }

//     @Test
//     @DisplayName("Controller should be authenticated")
//     void authenticate()  {
//         AuthenticationResponse response = AuthenticationResponse.builder()
//         .token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkeWxhbkBnbWFpbC5jb20iLCJpYXQiOjE3MDcxNzA3OTQsImV4cCI6MTcwNzI1NzE5NH0._O9fhQ0w7aMHDgnQ_FM4W25Xif4HY70WIVbUUsyXv-0")
//         .build();

//         String[] chunks = response.getToken().split("\\.");
//         Base64.Decoder decoder = Base64.getUrlDecoder();

//         String header = new String(decoder.decode(chunks[0]));
//         String payload = new String(decoder.decode(chunks[1]));

//         AuthenticationRequest request = AuthenticationRequest.builder()
//         .email("dylan@gmail.com")
//         .password("lalocomotora")
//         .build();


//         when(service.authenticate(any(AuthenticationRequest.class))).thenReturn(response);
//         AuthenticationResponse result = controller.authenticate(request);
//         .assertNotNull(result);
//         .assertEquals(request.getEmail(), result.getEmail());
//         .assertEquals(request.getPasword(), result.getPassword());

        
//     }

//     @Test
//     @DisplayName("Controller should be registered")
//     void register()  {
//         AuthenticationResponse response = AuthenticationResponse.builder()
//         .token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkeWxhbkBnbWFpbC5jb20iLCJpYXQiOjE3MDcxNzA3OTQsImV4cCI6MTcwNzI1NzE5NH0._O9fhQ0w7aMHDgnQ_FM4W25Xif4HY70WIVbUUsyXv-0")
//         .build();

//         when(null)
        
        
//     }