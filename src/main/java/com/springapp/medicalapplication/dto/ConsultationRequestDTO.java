package com.springapp.medicalapplication.dto;

import java.time.LocalDateTime;

public class ConsultationRequestDTO {
    public Long patientId;
    public Long doctorId;
    public LocalDateTime consultationDate;
    public String diagnosis;
    public String notes;
}