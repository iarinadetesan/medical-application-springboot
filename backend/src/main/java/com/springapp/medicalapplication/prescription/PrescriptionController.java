package com.springapp.medicalapplication.prescription;

import com.springapp.medicalapplication.prescription.dto.PrescriptionRequestDTO;
import com.springapp.medicalapplication.prescription.dto.PrescriptionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<List<PrescriptionResponseDTO>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/test")
    public String test() {
        return "PRESCRIPTIONS CONTROLLER OK";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDTO> getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionResponseDTO>> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<PrescriptionResponseDTO>> getPrescriptionsByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByDoctorId(doctorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PrescriptionResponseDTO>> getPrescriptionsByStatus(@PathVariable PrescriptionStatus status) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByStatus(status));
    }

    @PostMapping
    public ResponseEntity<?> createPrescription(@RequestBody PrescriptionRequestDTO prescription) {
        try {
            PrescriptionResponseDTO created = prescriptionService.createPrescription(prescription);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrescription(@PathVariable Long id, @RequestBody PrescriptionRequestDTO prescription) {
        try {
            PrescriptionResponseDTO updated = prescriptionService.updatePrescription(id, prescription);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable Long id) {
        try {
            prescriptionService.deletePrescription(id);
            return ResponseEntity.ok("Rețetă ștearsă cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}