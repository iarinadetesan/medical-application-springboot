package com.springapp.medicalapplication.consultation.dto;

import java.time.LocalDate;

public class ConsultationRequestDTO {
    public Long patientId;
    public Long doctorId;
    public LocalDate consultationDate;
    public String diagnosis;
    public String notes;
}