// ==================== AuthService.java ====================
package com.springapp.medicalapplication.service;

import com.springapp.medicalapplication.dto.LoginRequest;
import com.springapp.medicalapplication.dto.LoginResponse;
import com.springapp.medicalapplication.model.Doctor;
import com.springapp.medicalapplication.model.Patient;
import com.springapp.medicalapplication.model.User;
import com.springapp.medicalapplication.repository.DoctorRepository;
import com.springapp.medicalapplication.repository.PatientRepository;
import com.springapp.medicalapplication.repository.UserRepository;
import com.springapp.medicalapplication.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PatientRepository patientRepository,
                       DoctorRepository doctorRepository,
                       JwtTokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        System.out.println("🔐 Login attempt for: " + request.username);

        // Verifică dacă userul există
        User user = userRepository.findByUsername(request.username)
                .orElseThrow(() -> {
                    System.out.println("❌ User not found: " + request.username);
                    return new RuntimeException("User not found");
                });

        System.out.println("✅ User found: " + user.getUsername() + ", Role: " + user.getRole());
        System.out.println("🔑 Password in DB starts with: " + user.getPassword().substring(0, 10));

        // TEST MANUAL - verifică dacă parola se potrivește
        boolean passwordMatches = passwordEncoder.matches(request.password, user.getPassword());
        System.out.println("🧪 Manual password check: " + (passwordMatches ? "✅ MATCH" : "❌ NO MATCH"));

        // Autentificare
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username, request.password)
            );
            System.out.println("✅ Authentication successful!");
        } catch (Exception e) {
            System.out.println("❌ Authentication failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Username sau parolă greșite!");
        }

        // Generează token
        String token = tokenProvider.generateToken(
                user.getUsername(),
                user.getId(),
                user.getRole().name()
        );

        // Găsește profileId (Patient sau Doctor)
        Long profileId = null;
        if (user.getRole().name().equals("PATIENT")) {
            Optional<Patient> patient = patientRepository.findByUserId(user.getId());
            profileId = patient.map(Patient::getId).orElse(null);
        } else if (user.getRole().name().equals("DOCTOR")) {
            Optional<Doctor> doctor = doctorRepository.findByUserId(user.getId());
            profileId = doctor.map(Doctor::getId).orElse(null);
        }

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getId(),
                profileId
        );
    }
}