package com.springapp.medicalapplication.service;

import com.springapp.medicalapplication.dto.PrescriptionMapper;
import com.springapp.medicalapplication.dto.PrescriptionRequestDTO;
import com.springapp.medicalapplication.dto.PrescriptionResponseDTO;
import com.springapp.medicalapplication.model.Prescription;
import com.springapp.medicalapplication.model.PrescriptionStatus;
import com.springapp.medicalapplication.repository.ConsultationRepository;
import com.springapp.medicalapplication.repository.DoctorRepository;
import com.springapp.medicalapplication.repository.PatientRepository;
import com.springapp.medicalapplication.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ConsultationRepository consultationRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               PatientRepository patientRepository,
                               DoctorRepository doctorRepository,
                               ConsultationRepository consultationRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.consultationRepository = consultationRepository;
    }

    public List<PrescriptionResponseDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll()
                .stream()
                .map(PrescriptionMapper::toDto)
                .toList();
    }

    public Optional<PrescriptionResponseDTO> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id).map(PrescriptionMapper::toDto);
    }

    public List<PrescriptionResponseDTO> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepository.findByPatientIdOrderByIssueDateDesc(patientId)
                .stream()
                .map(PrescriptionMapper::toDto)
                .toList();
    }

    public List<PrescriptionResponseDTO> getPrescriptionsByDoctorId(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId)
                .stream()
                .map(PrescriptionMapper::toDto)
                .toList();
    }

    public List<PrescriptionResponseDTO> getPrescriptionsByStatus(PrescriptionStatus status) {
        return prescriptionRepository.findByStatus(status)
                .stream()
                .map(PrescriptionMapper::toDto)
                .toList();
    }

    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO req) {
        Prescription p = new Prescription();

        p.setPatient(patientRepository.findById(req.patientId)
                .orElseThrow(() -> new RuntimeException("Pacient inexistent!")));

        p.setDoctor(doctorRepository.findById(req.doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor inexistent!")));

        if (req.consultationId != null) {
            p.setConsultation(consultationRepository.findById(req.consultationId)
                    .orElseThrow(() -> new RuntimeException("Consultație inexistentă!")));
        }

        p.setMedicationName(req.medicationName);
        p.setDosage(req.dosage);
        p.setInstructions(req.instructions);
        p.setIssueDate(req.issueDate);
        p.setExpiryDate(req.expiryDate);

        if (req.status != null) {
            p.setStatus(req.status);
        }

        return PrescriptionMapper.toDto(prescriptionRepository.save(p));
    }

    public PrescriptionResponseDTO updatePrescription(Long id, PrescriptionRequestDTO req) {
        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rețeta nu a fost găsită!"));

        p.setMedicationName(req.medicationName);
        p.setDosage(req.dosage);
        p.setInstructions(req.instructions);
        p.setExpiryDate(req.expiryDate);

        if (req.status != null) {
            p.setStatus(req.status);
        }

        return PrescriptionMapper.toDto(prescriptionRepository.save(p));
    }

    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new RuntimeException("Rețeta nu a fost găsită!");
        }
        prescriptionRepository.deleteById(id);
    }
}
