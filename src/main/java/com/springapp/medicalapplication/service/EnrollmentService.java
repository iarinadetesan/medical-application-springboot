package com.springapp.medicalapplication.service;

import com.springapp.medicalapplication.dto.EnrollmentCreateRequestDTO;
import com.springapp.medicalapplication.dto.EnrollmentResponseDTO;
import com.springapp.medicalapplication.model.*;
import com.springapp.medicalapplication.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRequestRepository enrollRepo;
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;
    private final UserRepository userRepo;

    public EnrollmentService(EnrollmentRequestRepository enrollRepo,
                             PatientRepository patientRepo,
                             DoctorRepository doctorRepo,
                             UserRepository userRepo) {
        this.enrollRepo = enrollRepo;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.userRepo = userRepo;
    }

    // PATIENT: create request to a doctor
    public EnrollmentResponseDTO createRequest(String username, EnrollmentCreateRequestDTO req) {
        User u = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User inexistent"));
        if (u.getRole() != Role.PATIENT) throw new RuntimeException("Doar pacientul poate trimite cerere.");

        Patient patient = patientRepo.findByUserId(u.getId()).orElseThrow(() -> new RuntimeException("Profil pacient inexistent"));
        Doctor doctor = doctorRepo.findById(req.doctorId).orElseThrow(() -> new RuntimeException("Doctor inexistent"));

        // nu permitem duplicate PENDING
        enrollRepo.findByPatientIdAndDoctorId(patient.getId(), doctor.getId()).ifPresent(existing -> {
            if (existing.getStatus() == RequestStatus.PENDING) {
                throw new RuntimeException("Există deja o cerere PENDING către acest medic.");
            }
        });

        EnrollmentRequest er = new EnrollmentRequest();
        er.setPatient(patient);
        er.setDoctor(doctor);
        er.setStatus(RequestStatus.PENDING);
        // message optional: dacă vrei, mai adaugi câmp în entity

        return toDto(enrollRepo.save(er));
    }

    // PATIENT: my requests
    public List<EnrollmentResponseDTO> getMyRequests(String username) {
        User u = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User inexistent"));
        Patient patient = patientRepo.findByUserId(u.getId()).orElseThrow(() -> new RuntimeException("Profil pacient inexistent"));

        return enrollRepo.findByPatientId(patient.getId()).stream().map(this::toDto).toList();
    }

    // DOCTOR: pending requests to me
    public List<EnrollmentResponseDTO> getPendingForDoctor(String username) {
        User u = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User inexistent"));
        if (u.getRole() != Role.DOCTOR) throw new RuntimeException("Doar doctorul poate vedea cereri.");

        Doctor doctor = doctorRepo.findByUserId(u.getId()).orElseThrow(() -> new RuntimeException("Profil doctor inexistent"));
        return enrollRepo.findByDoctorIdAndStatus(doctor.getId(), RequestStatus.PENDING).stream().map(this::toDto).toList();
    }

    @Transactional
    public EnrollmentResponseDTO approve(String username, Long requestId, String reason) {
        User u = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User inexistent"));
        if (u.getRole() != Role.DOCTOR) throw new RuntimeException("Doar doctorul poate aproba.");

        Doctor doctor = doctorRepo.findByUserId(u.getId()).orElseThrow(() -> new RuntimeException("Profil doctor inexistent"));
        EnrollmentRequest er = enrollRepo.findById(requestId).orElseThrow(() -> new RuntimeException("Cerere inexistentă"));

        if (!er.getDoctor().getId().equals(doctor.getId())) throw new RuntimeException("Nu ai voie să aprobi această cerere.");
        if (er.getStatus() != RequestStatus.PENDING) throw new RuntimeException("Cererea nu mai este PENDING.");

        // set patient family doctor
        Patient patient = er.getPatient();
        patient.setFamilyDoctor(doctor);
        patientRepo.save(patient);

        er.setStatus(RequestStatus.APPROVED);
        er.setReviewedAt(LocalDateTime.now());
        er.setReviewReason(reason);
        return toDto(enrollRepo.save(er));
    }

    public EnrollmentResponseDTO reject(String username, Long requestId, String reason) {
        User u = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User inexistent"));
        if (u.getRole() != Role.DOCTOR) throw new RuntimeException("Doar doctorul poate respinge.");

        Doctor doctor = doctorRepo.findByUserId(u.getId()).orElseThrow(() -> new RuntimeException("Profil doctor inexistent"));
        EnrollmentRequest er = enrollRepo.findById(requestId).orElseThrow(() -> new RuntimeException("Cerere inexistentă"));

        if (!er.getDoctor().getId().equals(doctor.getId())) throw new RuntimeException("Nu ai voie să respingi această cerere.");
        if (er.getStatus() != RequestStatus.PENDING) throw new RuntimeException("Cererea nu mai este PENDING.");

        er.setStatus(RequestStatus.REJECTED);
        er.setReviewedAt(LocalDateTime.now());
        er.setReviewReason(reason);
        return toDto(enrollRepo.save(er));
    }

    private EnrollmentResponseDTO toDto(EnrollmentRequest er) {
        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.id = er.getId();
        dto.patientId = er.getPatient().getId();
        dto.doctorId = er.getDoctor().getId();
        dto.status = er.getStatus();
        dto.createdAt = er.getCreatedAt();
        dto.reviewedAt = er.getReviewedAt();
        dto.reviewReason = er.getReviewReason();
        return dto;
    }
}
