package com.springapp.medicalapplication.doctor.registration;
import com.springapp.medicalapplication.common.RequestStatus;
import com.springapp.medicalapplication.doctor.Doctor;
import com.springapp.medicalapplication.doctor.DoctorRepository;
import com.springapp.medicalapplication.notification.EmailService;
import com.springapp.medicalapplication.user.Role;
import com.springapp.medicalapplication.user.User;
import com.springapp.medicalapplication.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorRegistrationService {
    @Autowired
    private EmailService emailService;

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

        DoctorRegistrationRequest savedRequest = reqRepo.save(r);

        String statusLink = "http://localhost:5173/doctor-registration-status/" + savedRequest.getId();

        emailService.send(
                savedRequest.getEmail(),
                "Cererea ta de inregistrare a fost trimisa",
                "Salut, " + savedRequest.getFirstName() + "!\n\n"
                        + "Cererea ta de inregistrare ca medic a fost trimisa cu succes.\n\n"
                        + "Pentru a accesa statusul cererii, puteti accesa pagina aceasta:\n"
                        + statusLink
        );

        emailService.send(
                "aplicatie.medicala.info@gmail.com",
                "Cerere noua de inregistrare medic",
                "A fost creata o cerere noua pentru medic: "
                        + savedRequest.getFirstName() + " " + savedRequest.getLastName()
                        + "\nEmail: " + savedRequest.getEmail()
                        + "\nLicense: " + savedRequest.getLicenseNumber()
        );

        return toDto(savedRequest);


        

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

        emailService.send(
                r.getEmail(),
                "Cererea ta de cont a fost aprobată",
                "Salut, " + r.getFirstName() + "!\n\nCererea ta a fost aprobată."
                        + "\nTe poți autentifica folosind email: " + r.getUsername()
        );

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

        emailService.send(
                r.getEmail(),
                "Cererea ta de cont a fost respinsă",
                "Salut, " + r.getFirstName() + "!\n\nCererea ta a fost respinsă."
                        + "\nMotiv: " + r.getReviewReason()
                        + "\nDaca crezi ca am facut o greseala, nu ezita sa ne contactezi.\n"
        );
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
