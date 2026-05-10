package com.springapp.medicalapplication.patient.enrollment;

import com.springapp.medicalapplication.admin.ReviewRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins="*")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }
    @GetMapping("/test")
    public String test() {
        return "ENROLLMENT CONTROLLER OK";
    }
    // PATIENT
    @PostMapping
    public ResponseEntity<?> create(Authentication auth, @RequestBody EnrollmentCreateRequestDTO req) {
        try {
            return ResponseEntity.status(201).body(service.createRequest(auth.getName(), req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATIENT
    @GetMapping("/my")
    public ResponseEntity<List<EnrollmentResponseDTO>> my(Authentication auth) {
        return ResponseEntity.ok(service.getMyRequests(auth.getName()));
    }

    // DOCTOR
    @GetMapping("/pending")
    public ResponseEntity<List<EnrollmentResponseDTO>> pending(Authentication auth) {
        return ResponseEntity.ok(service.getPendingForDoctor(auth.getName()));
    }

    // DOCTOR
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(Authentication auth, @PathVariable Long id, @RequestBody(required = false) ReviewRequestDTO body) {
        try {
            return ResponseEntity.ok(service.approve(auth.getName(), id, body != null ? body.reason : null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DOCTOR
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(Authentication auth, @PathVariable Long id, @RequestBody(required = false) ReviewRequestDTO body) {
        try {
            return ResponseEntity.ok(service.reject(auth.getName(), id, body != null ? body.reason : null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
