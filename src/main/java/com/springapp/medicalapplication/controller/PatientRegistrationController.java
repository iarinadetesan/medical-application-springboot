package com.springapp.medicalapplication.controller;

import com.springapp.medicalapplication.dto.RegisterPatientRequest;
import com.springapp.medicalapplication.service.PatientRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient-register")
@CrossOrigin(origins = "*")
public class PatientRegistrationController {

    private final PatientRegistrationService service;

    public PatientRegistrationController(PatientRegistrationService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test() {
        return "PATIENT REGISTER CONTROLLER OK";
    }

    // PUBLIC: creează User + Patient și returnează LoginResponse (token + profileId)
    @PostMapping
    public ResponseEntity<?> registerPatient(@RequestBody RegisterPatientRequest req) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(service.registerPatient(req)); // ✅ corect: pe instanță
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
