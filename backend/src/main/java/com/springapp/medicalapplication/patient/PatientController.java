package com.springapp.medicalapplication.patient;
import com.springapp.medicalapplication.patient.dashboard.PatientDashboardService;
import com.springapp.medicalapplication.patient.dto.PatientRequestDTO;
import com.springapp.medicalapplication.patient.dto.PatientResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientDashboardService patientDashboardService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/test")
    public String test() {
        return "PATIENTS CONTROLLER OK";
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientByUserId(@PathVariable Long id) {
        return patientService.getPatientByUserId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnp/{cnp}")
    public ResponseEntity<PatientResponseDTO> getPatientByCnp(@PathVariable String cnp) {
        return patientService.getPatientByCnp(cnp)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/byfamilydoctorid/{doctorId}")
    public ResponseEntity<List<PatientResponseDTO>> getPatientByFamilyDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(patientService.getPatientByFamilyDoctorId(doctorId));
    }

    @GetMapping("/me")
    public ResponseEntity<PatientResponseDTO> getCurrentPatient(Authentication authentication) {
        String email = authentication.getName();

        return patientService.getPatientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/me/dashboard")
    public ResponseEntity<?> getPatientDashboard(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(patientDashboardService.getDashboardByEmail(email));
    }
    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody PatientRequestDTO patient) {
        try {
            PatientResponseDTO createdPatient = patientService.createPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody PatientRequestDTO patient) {
        try {
            PatientResponseDTO updatedPatient = patientService.updatePatient(id, patient);
            return ResponseEntity.ok(updatedPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok("Pacient șters cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }}
