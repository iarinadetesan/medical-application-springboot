package com.springapp.medicalapplication.util;

import com.springapp.medicalapplication.model.User;
import com.springapp.medicalapplication.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordTestConfig {

    @Bean
    public CommandLineRunner testPasswords(UserRepository userRepository,
                                           PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("\n========== TESTARE PAROLE ==========");

            User user = userRepository.findByUsername("dr.popescu").orElse(null);
            if (user != null) {
                System.out.println("Username: " + user.getUsername());
                System.out.println("Password in DB: " + user.getPassword());
                System.out.println("Password length: " + user.getPassword().length());

                String testPassword = "password123";
                boolean matches = passwordEncoder.matches(testPassword, user.getPassword());

                System.out.println("\nTestare password '" + testPassword + "': " + (matches ? "✅ MATCH" : "❌ NO MATCH"));

                // Generează o parolă nouă pentru comparație
                String newHash = passwordEncoder.encode(testPassword);
                System.out.println("\nHash nou generat: " + newHash);
                System.out.println("Hash nou matches: " + passwordEncoder.matches(testPassword, newHash));
            }

            System.out.println("====================================\n");
        };
    }
}