package com.springapp.medicalapplication.consultation.dto;

import java.time.LocalDateTime;

public class ConsultationResponseDTO {
    public Long id;
    public Long patientId;
    public String patientName;
    public Long doctorId;
    public String doctorName;
    public LocalDateTime consultationDate;
    public String diagnosis;
    public String notes;
    public LocalDateTime createdAt;

    public LocalDateTime getDate() {
        return consultationDate;
    }
}