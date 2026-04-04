package com.springapp.medicalapplication.service;

import com.springapp.medicalapplication.dto.ReferralMapper;
import com.springapp.medicalapplication.dto.ReferralRequestDTO;
import com.springapp.medicalapplication.dto.ReferralResponseDTO;
import com.springapp.medicalapplication.model.Referral;
import com.springapp.medicalapplication.model.ReferralStatus;
import com.springapp.medicalapplication.repository.DoctorRepository;
import com.springapp.medicalapplication.repository.PatientRepository;
import com.springapp.medicalapplication.repository.ReferralRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReferralService {

    private final ReferralRepository referralRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ReferralService(ReferralRepository referralRepository,
                           PatientRepository patientRepository,
                           DoctorRepository doctorRepository) {
        this.referralRepository = referralRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<ReferralResponseDTO> getAllReferrals() {
        return referralRepository.findAll()
                .stream()
                .map(ReferralMapper::toDto)
                .toList();
    }

    public Optional<ReferralResponseDTO> getReferralById(Long id) {
        return referralRepository.findById(id).map(ReferralMapper::toDto);
    }

    public List<ReferralResponseDTO> getReferralsByPatientId(Long patientId) {
        return referralRepository.findByPatientIdOrderByIssueDateDesc(patientId)
                .stream()
                .map(ReferralMapper::toDto)
                .toList();
    }

    public List<ReferralResponseDTO> getReferralsByDoctorId(Long doctorId) {
        return referralRepository.findByReferringDoctorId(doctorId)
                .stream()
                .map(ReferralMapper::toDto)
                .toList();
    }

    public List<ReferralResponseDTO> getReferralsByStatus(ReferralStatus status) {
        return referralRepository.findByStatus(status)
                .stream()
                .map(ReferralMapper::toDto)
                .toList();
    }

    public List<ReferralResponseDTO> getReferralsBySpecialization(String specialization) {
        return referralRepository.findBySpecialization(specialization)
                .stream()
                .map(ReferralMapper::toDto)
                .toList();
    }

    public ReferralResponseDTO createReferral(ReferralRequestDTO req) {
        Referral r = new Referral();

        r.setPatient(patientRepository.findById(req.patientId)
                .orElseThrow(() -> new RuntimeException("Pacient inexistent!")));

        r.setReferringDoctor(doctorRepository.findById(req.referringDoctorId)
                .orElseThrow(() -> new RuntimeException("Doctor inexistent!")));

        r.setSpecialization(req.specialization);
        r.setReason(req.reason);
        r.setIssueDate(req.issueDate);
        r.setExpiryDate(req.expiryDate);

        if (req.status != null) {
            r.setStatus(req.status);
        }

        return ReferralMapper.toDto(referralRepository.save(r));
    }

    public ReferralResponseDTO updateReferral(Long id, ReferralRequestDTO req) {
        Referral r = referralRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trimiterea nu a fost găsită!"));

        r.setSpecialization(req.specialization);
        r.setReason(req.reason);
        r.setExpiryDate(req.expiryDate);

        if (req.status != null) {
            r.setStatus(req.status);
        }

        return ReferralMapper.toDto(referralRepository.save(r));
    }

    public void deleteReferral(Long id) {
        if (!referralRepository.existsById(id)) {
            throw new RuntimeException("Trimiterea nu a fost găsită!");
        }
        referralRepository.deleteById(id);
    }
}