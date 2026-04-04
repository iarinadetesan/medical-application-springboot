package com.springapp.medicalapplication.repository;

import com.springapp.medicalapplication.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUserId(Long userId);

    Optional<Patient> findByCnp(String cnp);

    List<Patient> findByFamilyDoctorId(Long doctorId);

    boolean existsByCnp(String cnp);

    boolean existsByUserId(Long id);

   // boolean existsById(Long id);

}