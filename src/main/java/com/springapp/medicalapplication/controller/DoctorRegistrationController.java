package com.springapp.medicalapplication.controller;

import com.springapp.medicalapplication.dto.*;
import com.springapp.medicalapplication.model.RequestStatus;
import com.springapp.medicalapplication.service.DoctorRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-requests")
@CrossOrigin(origins = "*")
public class DoctorRegistrationController {

    private final DoctorRegistrationService service;

    public DoctorRegistrationController(DoctorRegistrationService service) {
        this.service = service;
    }

    // PUBLIC: doctorul trimite cerere
    @PostMapping("/create")
    public ResponseEntity<?> submit(@RequestBody RegisterDoctorRequest req) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createRequest(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/test")
    public String test() {
            return "DOCTOR REQUEST CONTROLLER OK";
    }

    // ADMIN: listă cereri
    @GetMapping
    public ResponseEntity<List<DoctorRegistrationResponseDTO>> list(@RequestParam(defaultValue = "PENDING") RequestStatus status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    // ADMIN: approve
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id, @RequestBody(required = false) ReviewRequestDTO body) {
        try {
            String reason = body != null ? body.reason : null;
            return ResponseEntity.ok(service.approve(id, reason));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ADMIN: reject
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id, @RequestBody(required = false) ReviewRequestDTO body) {
        try {
            String reason = body != null ? body.reason : null;
            return ResponseEntity.ok(service.reject(id, reason));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
