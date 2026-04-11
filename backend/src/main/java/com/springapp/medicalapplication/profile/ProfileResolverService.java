package com.springapp.medicalapplication.profile;

import com.springapp.medicalapplication.doctor.Doctor;
import com.springapp.medicalapplication.doctor.DoctorRepository;
import com.springapp.medicalapplication.patient.Patient;
import com.springapp.medicalapplication.patient.PatientRepository;
import com.springapp.medicalapplication.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileResolverService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ProfileResolverService(PatientRepository patientRepository,
                                  DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public Long resolveProfileId(User user) {
        if (user.getRole().name().equals("PATIENT")) {
            Optional<Patient> patient = patientRepository.findByUserId(user.getId());
            return patient.map(Patient::getId).orElse(null);
        }

        if (user.getRole().name().equals("DOCTOR")) {
            Optional<Doctor> doctor = doctorRepository.findByUserId(user.getId());
            return doctor.map(Doctor::getId).orElse(null);
        }

        return null;
    }
}
