
package com.springapp.medicalapplication.auth;

import com.springapp.medicalapplication.auth.dto.LoginRequest;
import com.springapp.medicalapplication.auth.dto.LoginResponse;
import com.springapp.medicalapplication.common.InvalidCredentialsException;
import com.springapp.medicalapplication.common.ResourceNotFoundException;
import com.springapp.medicalapplication.doctor.Doctor;
import com.springapp.medicalapplication.patient.Patient;
import com.springapp.medicalapplication.profile.ProfileResolverService;
import com.springapp.medicalapplication.user.User;
import com.springapp.medicalapplication.doctor.DoctorRepository;
import com.springapp.medicalapplication.patient.PatientRepository;
import com.springapp.medicalapplication.user.UserRepository;
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
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ProfileResolverService profileResolverService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       JwtTokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder,
                       ProfileResolverService profileResolverService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.profileResolverService = profileResolverService;
    }

    public LoginResponse login(LoginRequest request) {
        System.out.println("Login attempt for: " + request.email);


        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> {
                    System.out.println("User not found: " + request.email);
                    return new ResourceNotFoundException("User not found");
                });

        System.out.println("User found: " + user.getUsername() + ", Role: " + user.getRole());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email, request.password)
            );
            System.out.println("Authentication successful!");
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            e.printStackTrace();
            throw new InvalidCredentialsException("Username sau parolă greșite!");
        }


        String token = tokenProvider.generateToken(
                user.getEmail(),
                user.getId(),
                user.getRole().name()
        );


        Long profileId = null;

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