package com.springapp.medicalapplication.doctor;

import com.springapp.medicalapplication.doctor.dto.DoctorMapper;
import com.springapp.medicalapplication.doctor.dto.DoctorRequestDTO;
import com.springapp.medicalapplication.doctor.dto.DoctorResponseDTO;
import com.springapp.medicalapplication.patient.PatientRepository;
import com.springapp.medicalapplication.patient.dto.PatientMapper;
import com.springapp.medicalapplication.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
   // private final PatientRepository patientRepository;

    public DoctorService(
                          UserRepository userRepository,
                          DoctorRepository doctorRepository) {

        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorMapper::toDto)
                .toList();
    }

    public Optional<DoctorResponseDTO> getDoctorByUserId(Long userId) {
        return doctorRepository.findByUserId(userId).map(DoctorMapper::toDto);
    }
    //face un join intre tabelul de useri si tabelul de medici ca sa gaseasca medicul cautat dupa email
    public Optional<DoctorResponseDTO> getDoctorByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(user -> doctorRepository.findByUserId(user.getId()))
                .map(DoctorMapper::toDto);
    }

    /** DE ADAUGAT: functie in doctorRepository care sa returneze toti pacientii doctorului;
     * public Optional<DoctorResponseDTO> getDoctorAllPacients(Long userId) {
        return doctorRepository.findByUserId(userId).map(DoctorMapper::toDto);
    }*/



    public DoctorResponseDTO createDoctor(DoctorRequestDTO req) {
        if (doctorRepository.existsById(req.id)) {
            throw new RuntimeException("Un doctor cu acest ID există deja!");
        }

        Doctor doctor = new Doctor();
        doctor.setUser(userRepository.findById(req.userId)
                .orElseThrow(() -> new RuntimeException("User inexistent!")));

        doctor.setFirstName(req.firstName);
        doctor.setLastName(req.lastName);
        doctor.setCabinetAddress(req.cabinetAddress);
       // doctor.setPatients(req.patients);
        doctor.setLicenseNumber(req.licenseNumber);
        doctor.setPhone(req.phone);


        return DoctorMapper.toDto(doctorRepository.save(doctor));
    }

    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO req) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctorul nu a fost găsit!"));


        doctor.setFirstName(req.firstName);
        doctor.setLastName(req.lastName);
        doctor.setCabinetAddress(req.cabinetAddress);
       // doctor.setPatients(req.patients);
        doctor.setLicenseNumber(req.licenseNumber);
        doctor.setPhone(req.phone);

        return DoctorMapper.toDto(doctorRepository.save(doctor));


    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctorul nu a fost găsit!");
        }
        doctorRepository.deleteById(id);
    }
}
