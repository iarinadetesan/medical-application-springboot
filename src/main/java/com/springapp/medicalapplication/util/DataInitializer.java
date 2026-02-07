package com.springapp.medicalapplication.util;

import com.springapp.medicalapplication.model.Role;
import com.springapp.medicalapplication.model.User;
import com.springapp.medicalapplication.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifică dacă există useri deja
        if (userRepository.count() > 0) {
            System.out.println("✅ Baza de date are deja useri.");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            System.out.println(encoder.encode("password123"));



            // Actualizează parolele existente să fie criptate
           // updatePasswordsIfNeeded();
            return;
        }

        System.out.println("🔄 Inițializare date...");
        // Aici poți adăuga useri noi dacă vrei
    }

    /** private void updatePasswordsIfNeeded() {
        // Găsește toți userii
        var users = userRepository.findAll();

        for (User user : users) {
            // Verifică dacă parola nu e deja criptată (BCrypt începe cu $2a$)
            if (!user.getPassword().startsWith("$2a$")) {
                System.out.println("🔐 Criptez parola pentru: " + user.getUsername());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            }
        }

        System.out.println("✅ Toate parolele sunt criptate!");
    } */
}