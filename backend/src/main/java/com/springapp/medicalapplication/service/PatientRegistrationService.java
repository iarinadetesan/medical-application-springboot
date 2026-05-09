package com.springapp.medicalapplication.service;

import com.springapp.medicalapplication.auth.dto.LoginResponse;
import com.springapp.medicalapplication.dto.RegisterPatientRequest;
import com.springapp.medicalapplication.patient.Patient;
import com.springapp.medicalapplication.user.Role;
import com.springapp.medicalapplication.user.User;
import com.springapp.medicalapplication.patient.PatientRepository;
import com.springapp.medicalapplication.user.UserRepository;
import com.springapp.medicalapplication.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientRegistrationService {

    private final UserRepository userRepo;
    private final PatientRepository patientRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public PatientRegistrationService(UserRepository userRepo,
                               PatientRepository patientRepo,
                               PasswordEncoder passwordEncoder,
                               JwtTokenProvider tokenProvider) {
        this.userRepo = userRepo;
        this.patientRepo = patientRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public LoginResponse registerPatient(RegisterPatientRequest req) {
        if (userRepo.existsByUsername(req.username)) throw new RuntimeException("Username deja folosit!");
        if (userRepo.existsByEmail(req.email)) throw new RuntimeException("Email deja folosit!");
        if (patientRepo.existsByCnp(req.cnp)) throw new RuntimeException("CNP deja folosit!");

        User user = new User();
        user.setUsername(req.username);
        user.setEmail(req.email);
        user.setPassword(passwordEncoder.encode(req.password));
        user.setRole(Role.PATIENT);
        User savedUser = userRepo.save(user);

        Patient p = new Patient();
        p.setUser(savedUser);
        p.setFirstName(req.firstName);
        p.setLastName(req.lastName);
        p.setCnp(req.cnp);
        p.setDateOfBirth(req.dateOfBirth);
        p.setGender(req.gender);
        p.setPhone(req.phone);
        p.setAddress(req.address);
        Patient savedPatient = patientRepo.save(p);

        String token = tokenProvider.generateToken(
                savedUser.getEmail(),
                savedUser.getId(),
                savedUser.getRole().name()
        );

        return new LoginResponse(
                token,
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                savedUser.getId(),
                null
        );
    }
}
