package com.springapp.medicalapplication.referral;

import com.springapp.medicalapplication.referral.dto.ReferralRequestDTO;
import com.springapp.medicalapplication.referral.dto.ReferralResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referrals")
@CrossOrigin(origins = "*")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @GetMapping
    public ResponseEntity<List<ReferralResponseDTO>> getAllReferrals() {
        return ResponseEntity.ok(referralService.getAllReferrals());
    }

    @GetMapping("/test")
    public String test() {
        return "REFERRALS CONTROLLER OK";
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReferralResponseDTO> getReferralById(@PathVariable Long id) {
        return referralService.getReferralById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ReferralResponseDTO>> getReferralsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(referralService.getReferralsByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ReferralResponseDTO>> getReferralsByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(referralService.getReferralsByDoctorId(doctorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReferralResponseDTO>> getReferralsByStatus(@PathVariable ReferralStatus status) {
        return ResponseEntity.ok(referralService.getReferralsByStatus(status));
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<ReferralResponseDTO>> getReferralsBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(referralService.getReferralsBySpecialization(specialization));
    }

    @PostMapping
    public ResponseEntity<?> createReferral(@RequestBody ReferralRequestDTO referral) {
        try {
            ReferralResponseDTO created = referralService.createReferral(referral);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReferral(@PathVariable Long id, @RequestBody ReferralRequestDTO referral) {
        try {
            ReferralResponseDTO updated = referralService.updateReferral(id, referral);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReferral(@PathVariable Long id) {
        try {
            referralService.deleteReferral(id);
            return ResponseEntity.ok("Trimitere ștearsă cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}