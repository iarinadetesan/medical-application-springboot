package com.springapp.medicalapplication.controller;

import com.springapp.medicalapplication.dto.DoctorRequestDTO;
import com.springapp.medicalapplication.dto.DoctorResponseDTO;
import com.springapp.medicalapplication.dto.PatientRequestDTO;
import com.springapp.medicalapplication.dto.PatientResponseDTO;
import com.springapp.medicalapplication.service.DoctorService;
import com.springapp.medicalapplication.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import com.springapp.medicalapplication.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/test")
    public String test() {
        return "DOCTORS CONTROLLER OK";
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorByUserId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



   /** DE ADAUGAT: endpoint pe /doctors care sa returneze toti pacientii doctorului;
    *  @GetMapping("/byfamilydoctorid/{doctorId}")
    public ResponseEntity<List<PatientResponseDTO>> getPatientByFamilyDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.get);
    } */

    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody DoctorRequestDTO doctor) {
        try {
            DoctorResponseDTO createdDoctor = doctorService.createDoctor(doctor);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody DoctorRequestDTO doctor) {
        try {
            DoctorResponseDTO updatedDoctor = doctorService.updateDoctor(id, doctor);
            return ResponseEntity.ok(updatedDoctor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok("Doctor șters cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }}