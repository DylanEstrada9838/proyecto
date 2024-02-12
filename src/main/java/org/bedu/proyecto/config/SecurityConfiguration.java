package org.bedu.proyecto.config;


import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationJwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
CorsConfigurationSource corsConfigurationSource() {
	CorsConfiguration configuration = new CorsConfiguration();
	configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
	configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setExposedHeaders(Arrays.asList("*"));
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/**", configuration);
	return source;
}
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       http.csrf(csrf -> csrf.disable());
       http.cors(cors->cors.configurationSource(corsConfigurationSource()));
       http.authorizeHttpRequests(request->{
        request.requestMatchers("/users/sign-in","/users/sign-up").permitAll();
        request.requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN");
        request.anyRequest().authenticated();
       });
       http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       http.authenticationProvider(authenticationProvider);
       http.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
       return http.build();
}
}