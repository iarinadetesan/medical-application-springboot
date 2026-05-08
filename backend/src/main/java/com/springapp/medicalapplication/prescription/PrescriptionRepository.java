package com.springapp.medicalapplication.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatientId(Long patientId);

    List<Prescription> findByDoctorId(Long doctorId);

    List<Prescription> findByPatientIdOrderByIssueDateDesc(Long patientId);

    List<Prescription> findByPatientIdAndStatus(Long patientId, PrescriptionStatus status);

    List<Prescription> findByStatus(PrescriptionStatus status);

    List<Prescription> findByExpiryDateBefore(LocalDate date);

    List<Prescription> findByStatusAndExpiryDateBefore(
            PrescriptionStatus status,
            LocalDate date
    );
}