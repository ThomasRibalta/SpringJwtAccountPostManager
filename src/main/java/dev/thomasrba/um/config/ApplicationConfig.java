package dev.thomasrba.um.config;

import dev.thomasrba.um.exception.UserNotFoundException;
import dev.thomasrba.um.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * This class serves as the configuration for Spring Security in the application.
 * It defines beans for user authentication and password encoding.
 *
 * The class uses Lombok's @RequiredArgsConstructor to generate a constructor
 * that accepts final fields, allowing for dependency injection of UserRepository.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    public final UserRepository userRepository;

    /*
     * This bean defines a UserDetailsService that retrieves user details
     * based on the username (email in this case). If the user is not found,
     * it throws a UserNotFoundException.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(() ->
                new UserNotFoundException("UserDetailsService returned null"));
    }

    /*
     * This bean creates an AuthenticationProvider that uses the custom UserDetailsService
     * and a PasswordEncoder (BCryptPasswordEncoder) to authenticate users.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /*
     * This bean provides an AuthenticationManager, which is the main interface
     * for authentication in Spring Security. It is configured using the
     * AuthenticationConfiguration object provided by Spring.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     * This bean defines a PasswordEncoder using BCrypt, which is a strong
     * hashing algorithm for securely storing passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
