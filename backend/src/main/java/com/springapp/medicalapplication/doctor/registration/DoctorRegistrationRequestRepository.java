package com.springapp.medicalapplication.doctor.registration;

import com.springapp.medicalapplication.common.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRegistrationRequestRepository extends JpaRepository<DoctorRegistrationRequest, Long> {
    List<DoctorRegistrationRequest> findByStatus(RequestStatus status);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<DoctorRegistrationRequest> findByEmail(String email);
}
