package com.springapp.medicalapplication.service;

import com.springapp.medicalapplication.dto.*;
import com.springapp.medicalapplication.model.*;
import com.springapp.medicalapplication.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorRegistrationService {

    private final DoctorRegistrationRequestRepository reqRepo;
    private final UserRepository userRepo;
    private final DoctorRepository doctorRepo;
    private final PasswordEncoder passwordEncoder;

    public DoctorRegistrationService(DoctorRegistrationRequestRepository reqRepo,
                                     UserRepository userRepo,
                                     DoctorRepository doctorRepo,
                                     PasswordEncoder passwordEncoder) {
        this.reqRepo = reqRepo;
        this.userRepo = userRepo;
        this.doctorRepo = doctorRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public DoctorRegistrationResponseDTO createRequest(RegisterDoctorRequest req) {
        // validări unice (și în users, și în requests)
        if (userRepo.existsByEmail(req.email) || reqRepo.existsByEmail(req.email)) {
            throw new RuntimeException("Email deja folosit!");
        }
        if (userRepo.existsByUsername(req.username) || reqRepo.existsByUsername(req.username)) {
            throw new RuntimeException("Username deja folosit!");
        }
        if (doctorRepo.existsByLicenseNumber(req.licenseNumber) || reqRepo.existsByLicenseNumber(req.licenseNumber)) {
            throw new RuntimeException("Numărul de licență/parafă există deja!");
        }

        DoctorRegistrationRequest r = new DoctorRegistrationRequest();
        r.setUsername(req.username);
        r.setEmail(req.email);
        r.setPasswordHash(passwordEncoder.encode(req.password));
        r.setFirstName(req.firstName);
        r.setLastName(req.lastName);
        r.setLicenseNumber(req.licenseNumber);
        r.setPhone(req.phone);
        r.setCabinetAddress(req.cabinetAddress);
        r.setStatus(RequestStatus.PENDING);

        return toDto(reqRepo.save(r));
    }

    public List<DoctorRegistrationResponseDTO> getByStatus(RequestStatus status) {
        return reqRepo.findByStatus(status).stream().map(this::toDto).toList();
    }

    public DoctorRegistrationResponseDTO getById(Long id) {
        return toDto(reqRepo.findById(id).orElseThrow(() -> new RuntimeException("Cerere inexistentă")));
    }

    @Transactional
    public DoctorRegistrationResponseDTO approve(Long requestId, String reason) {
        DoctorRegistrationRequest r = reqRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Cerere inexistentă"));

        if (r.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Cererea nu mai este PENDING.");
        }

        // creează user + doctor
        User user = new User();
        user.setUsername(r.getUsername());
        user.setEmail(r.getEmail());
        user.setPassword(r.getPasswordHash());
        user.setRole(Role.DOCTOR);
        User savedUser = userRepo.save(user);

        Doctor doc = new Doctor();
        doc.setUser(savedUser);
        doc.setFirstName(r.getFirstName());
        doc.setLastName(r.getLastName());
        doc.setLicenseNumber(r.getLicenseNumber());
        doc.setPhone(r.getPhone());
        doc.setCabinetAddress(r.getCabinetAddress());
        doctorRepo.save(doc);

        r.setStatus(RequestStatus.APPROVED);
        r.setReviewedAt(LocalDateTime.now());
        r.setReviewReason(reason);
        return toDto(reqRepo.save(r));
    }

    public DoctorRegistrationResponseDTO reject(Long requestId, String reason) {
        DoctorRegistrationRequest r = reqRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Cerere inexistentă"));

        if (r.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Cererea nu mai este PENDING.");
        }

        r.setStatus(RequestStatus.REJECTED);
        r.setReviewedAt(LocalDateTime.now());
        r.setReviewReason(reason);
        return toDto(reqRepo.save(r));
    }

    private DoctorRegistrationResponseDTO toDto(DoctorRegistrationRequest r) {
        DoctorRegistrationResponseDTO dto = new DoctorRegistrationResponseDTO();
        dto.id = r.getId();
        dto.username = r.getUsername();
        dto.email = r.getEmail();
        dto.firstName = r.getFirstName();
        dto.lastName = r.getLastName();
        dto.licenseNumber = r.getLicenseNumber();
        dto.phone = r.getPhone();
        dto.cabinetAddress = r.getCabinetAddress();
        dto.status = r.getStatus();
        dto.createdAt = r.getCreatedAt();
        dto.reviewedAt = r.getReviewedAt();
        dto.reviewReason = r.getReviewReason();
        return dto;
    }
}
