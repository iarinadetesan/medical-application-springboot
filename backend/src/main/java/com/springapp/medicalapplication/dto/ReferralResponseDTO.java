package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.ReferralStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReferralResponseDTO {
    public Long id;
    public Long patientId;
    public String patientName;
    public Long referringDoctorId;
    public String referringDoctorName;
    public String specialization;
    public String reason;
    public LocalDate issueDate;
    public LocalDate expiryDate;
    public ReferralStatus status;
    public LocalDateTime createdAt;
}