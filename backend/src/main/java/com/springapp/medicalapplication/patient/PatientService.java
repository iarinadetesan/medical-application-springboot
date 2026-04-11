package com.springapp.medicalapplication.patient;

import com.springapp.medicalapplication.patient.dto.PatientMapper;
import com.springapp.medicalapplication.patient.dto.PatientRequestDTO;
import com.springapp.medicalapplication.patient.dto.PatientResponseDTO;
import com.springapp.medicalapplication.doctor.DoctorRepository;
import com.springapp.medicalapplication.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public PatientService(PatientRepository patientRepository,
                          UserRepository userRepository,
                          DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(PatientMapper::toDto)
                .toList();
    }

    public Optional<PatientResponseDTO> getPatientByUserId(Long userId) {
        return patientRepository.findByUserId(userId).map(PatientMapper::toDto);
    }

    public Optional<PatientResponseDTO> getPatientByCnp(String cnp) {
        return patientRepository.findByCnp(cnp).map(PatientMapper::toDto);
    }

    public Optional<PatientResponseDTO> getPatientByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(user -> patientRepository.findByUserId(user.getId()))
                .map(PatientMapper::toDto);
    }

    public List<PatientResponseDTO> getPatientByFamilyDoctorId(Long doctorId) {
        return patientRepository.findByFamilyDoctorId(doctorId)
                .stream()
                .map(PatientMapper::toDto)
                .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO req) {
        if (patientRepository.existsByUserId(req.userId)) {
            throw new RuntimeException("Un pacient cu acest user id există deja!");
        }

        if (patientRepository.existsByCnp(req.cnp)) {
            throw new RuntimeException("Un pacient cu acest CNP există deja!");
        }

        Patient p = new Patient();
        p.setUser(userRepository.findById(req.userId)
                .orElseThrow(() -> new RuntimeException("User inexistent!")));

        p.setFirstName(req.firstName);
        p.setLastName(req.lastName);
        p.setCnp(req.cnp);
        p.setDateOfBirth(req.dateOfBirth);
        p.setGender(req.gender);
        p.setPhone(req.phone);
        p.setAddress(req.address);

        if (req.familyDoctorId != null) {
            p.setFamilyDoctor(doctorRepository.findById(req.familyDoctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor inexistent!")));
        }

        return PatientMapper.toDto(patientRepository.save(p));
    }

    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO req) {
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacientul nu a fost găsit!"));


        p.setFirstName(req.firstName);
        p.setLastName(req.lastName);
        p.setCnp(req.cnp);
        p.setDateOfBirth(req.dateOfBirth);
        p.setGender(req.gender);
        p.setPhone(req.phone);
        p.setAddress(req.address);

        if (req.familyDoctorId != null) {
            p.setFamilyDoctor(doctorRepository.findById(req.familyDoctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor inexistent!")));
        } else {
            p.setFamilyDoctor(null);
        }

        return PatientMapper.toDto(patientRepository.save(p));
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Pacientul nu a fost găsit!");
        }
        patientRepository.deleteById(id);
    }
}
