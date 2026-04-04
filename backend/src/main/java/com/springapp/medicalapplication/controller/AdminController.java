package com.springapp.medicalapplication.controller;



import com.springapp.medicalapplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private ConsultationRepository consultationRepository;


    @DeleteMapping("/reset-database")
    public ResponseEntity<String> resetDatabase() {
        try {

            return ResponseEntity.ok(
                    "Baza de date a fost golit cu succes!\n" +
                            "Repornește aplicația pentru a recrea datele de test."
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }

    @GetMapping("/database-info")
    public ResponseEntity<String> getDatabaseInfo() {
        String info = "===========================================\n" +
                "Informații Bază de Date:\n" +
                "- " + userRepository.count() + " utilizatori\n" +
                "- " + doctorRepository.count() + " doctori\n" +
                "- " + patientRepository.count() + " pacienti\n" +
                "- " + prescriptionRepository.count() + " retete\n" +
                "- " + referralRepository.count() + " trimiteri\n" +
                "- " + consultationRepository.count() + " consultatii efectuate\n" +

                "===========================================";
        return ResponseEntity.ok(info);
    }
}