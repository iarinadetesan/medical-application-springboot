package com.springapp.medicalapplication.referral;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {

    List<Referral> findByPatientId(Long patientId);

    List<Referral> findByReferringDoctorId(Long doctorId);

    List<Referral> findByPatientIdOrderByIssueDateDesc(Long patientId);

    List<Referral> findByPatientIdAndStatus(Long patientId, ReferralStatus status);

    List<Referral> findByStatus(ReferralStatus status);

    List<Referral> findByExpiryDateBefore(LocalDate date);

    List<Referral> findBySpecialization(String specialization);
}