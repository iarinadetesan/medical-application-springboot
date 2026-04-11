package com.springapp.medicalapplication.consultation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    List<Consultation> findByPatientId(Long patientId);

    List<Consultation> findByDoctorId(Long doctorId);

    List<Consultation> findByPatientIdOrderByConsultationDateDesc(Long patientId);

    List<Consultation> findByDoctorIdOrderByConsultationDateDesc(Long doctorId);

    @Query("SELECT c FROM Consultation c WHERE c.patient.id = :patientId " +
            "AND c.consultationDate BETWEEN :startDate AND :endDate")
    List<Consultation> findByPatientAndDateRange(
            @Param("patientId") Long patientId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}