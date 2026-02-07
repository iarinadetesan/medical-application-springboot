// ==================== ConsultationController.java ====================
package com.springapp.medicalapplication.controller;

import com.springapp.medicalapplication.dto.ConsultationRequestDTO;
import com.springapp.medicalapplication.dto.ConsultationResponseDTO;
import com.springapp.medicalapplication.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@CrossOrigin(origins = "*")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public ResponseEntity<List<ConsultationResponseDTO>> getAllConsultations() {
        return ResponseEntity.ok(consultationService.getAllConsultations());
    }

    @GetMapping("/test")
    public String test() {
        return "CONSULTATIONS CONTROLLER OK";
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponseDTO> getConsultationById(@PathVariable Long id) {
        return consultationService.getConsultationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ConsultationResponseDTO>> getConsultationsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(consultationService.getConsultationsByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ConsultationResponseDTO>> getConsultationsByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(consultationService.getConsultationsByDoctorId(doctorId));
    }

    @PostMapping
    public ResponseEntity<?> createConsultation(@RequestBody ConsultationRequestDTO consultation) {
        try {
            ConsultationResponseDTO created = consultationService.createConsultation(consultation);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConsultation(@PathVariable Long id, @RequestBody ConsultationRequestDTO consultation) {
        try {
            ConsultationResponseDTO updated = consultationService.updateConsultation(id, consultation);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConsultation(@PathVariable Long id) {
        try {
            consultationService.deleteConsultation(id);
            return ResponseEntity.ok("Consultație ștearsă cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}