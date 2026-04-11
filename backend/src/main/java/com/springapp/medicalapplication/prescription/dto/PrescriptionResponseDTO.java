package com.springapp.medicalapplication.prescription.dto;

import com.springapp.medicalapplication.prescription.PrescriptionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PrescriptionResponseDTO {
    public Long id;
    public Long patientId;
    public String patientName;
    public Long doctorId;
    public String doctorName;
    public Long consultationId;
    public String medicationName;
    public String dosage;
    public String instructions;
    public LocalDate issueDate;
    public LocalDate expiryDate;
    public PrescriptionStatus status;
    public LocalDateTime createdAt;
}