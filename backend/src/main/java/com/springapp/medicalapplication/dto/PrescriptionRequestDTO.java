package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.PrescriptionStatus;
import java.time.LocalDate;

public class PrescriptionRequestDTO {
    public Long patientId;
    public Long doctorId;
    public Long consultationId;
    public String medicationName;
    public String dosage;
    public String instructions;
    public LocalDate issueDate;
    public LocalDate expiryDate;
    public PrescriptionStatus status;
}

