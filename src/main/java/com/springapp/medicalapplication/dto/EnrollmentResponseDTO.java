package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.RequestStatus;
import java.time.LocalDateTime;

public class EnrollmentResponseDTO {
    public Long id;
    public Long patientId;
    public Long doctorId;
    public RequestStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime reviewedAt;
    public String reviewReason;
}
