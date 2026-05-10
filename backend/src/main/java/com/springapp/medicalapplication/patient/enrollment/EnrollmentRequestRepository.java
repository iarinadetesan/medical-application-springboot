package com.springapp.medicalapplication.patient.enrollment;

import com.springapp.medicalapplication.common.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, Long> {
    List<EnrollmentRequest> findByDoctorIdAndStatus(Long doctorId, RequestStatus status);
    List<EnrollmentRequest> findByPatientId(Long patientId);
    Optional<EnrollmentRequest> findByPatientIdAndDoctorId(Long patientId, Long doctorId);
}
