package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.ReferralStatus;
import java.time.LocalDate;

public class ReferralRequestDTO {
    public Long patientId;
    public Long referringDoctorId;
    public String specialization;
    public String reason;
    public LocalDate issueDate;
    public LocalDate expiryDate;
    public ReferralStatus status;
}