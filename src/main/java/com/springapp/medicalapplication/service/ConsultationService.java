

// ==================== ConsultationService.java ====================
package com.springapp.medicalapplication.service;

import com.springapp.medicalapplication.dto.ConsultationMapper;
import com.springapp.medicalapplication.dto.ConsultationRequestDTO;
import com.springapp.medicalapplication.dto.ConsultationResponseDTO;
import com.springapp.medicalapplication.model.Consultation;
import com.springapp.medicalapplication.repository.ConsultationRepository;
import com.springapp.medicalapplication.repository.DoctorRepository;
import com.springapp.medicalapplication.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ConsultationService(ConsultationRepository consultationRepository,
                               PatientRepository patientRepository,
                               DoctorRepository doctorRepository) {
        this.consultationRepository = consultationRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<ConsultationResponseDTO> getAllConsultations() {
        return consultationRepository.findAll()
                .stream()
                .map(ConsultationMapper::toDto)
                .toList();
    }

    public Optional<ConsultationResponseDTO> getConsultationById(Long id) {
        return consultationRepository.findById(id).map(ConsultationMapper::toDto);
    }

    public List<ConsultationResponseDTO> getConsultationsByPatientId(Long patientId) {
        return consultationRepository.findByPatientIdOrderByConsultationDateDesc(patientId)
                .stream()
                .map(ConsultationMapper::toDto)
                .toList();
    }

    public List<ConsultationResponseDTO> getConsultationsByDoctorId(Long doctorId) {
        return consultationRepository.findByDoctorIdOrderByConsultationDateDesc(doctorId)
                .stream()
                .map(ConsultationMapper::toDto)
                .toList();
    }

    public ConsultationResponseDTO createConsultation(ConsultationRequestDTO req) {
        Consultation c = new Consultation();

        c.setPatient(patientRepository.findById(req.patientId)
                .orElseThrow(() -> new RuntimeException("Pacient inexistent!")));

        c.setDoctor(doctorRepository.findById(req.doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor inexistent!")));

        c.setConsultationDate(req.consultationDate);
        c.setDiagnosis(req.diagnosis);
        c.setNotes(req.notes);

        return ConsultationMapper.toDto(consultationRepository.save(c));
    }

    public ConsultationResponseDTO updateConsultation(Long id, ConsultationRequestDTO req) {
        Consultation c = consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultația nu a fost găsită!"));

        c.setConsultationDate(req.consultationDate);
        c.setDiagnosis(req.diagnosis);
        c.setNotes(req.notes);

        return ConsultationMapper.toDto(consultationRepository.save(c));
    }

    public void deleteConsultation(Long id) {
        if (!consultationRepository.existsById(id)) {
            throw new RuntimeException("Consultația nu a fost găsită!");
        }
        consultationRepository.deleteById(id);
    }
}

