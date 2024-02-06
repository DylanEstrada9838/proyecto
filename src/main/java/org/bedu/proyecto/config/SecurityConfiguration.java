package org.bedu.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationJwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       http.csrf(csrf -> csrf.disable());
       http.cors(cors->cors.disable());
       http.authorizeHttpRequests(request->{
        request.requestMatchers("/users/sign-in","/users/sign-up").permitAll();
        request.requestMatchers(HttpMethod.GET,"/users","/clients").hasRole("ADMIN");
        request.anyRequest().authenticated();
       });
       http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       http.authenticationProvider(authenticationProvider);
       http.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
       return http.build();
}
}