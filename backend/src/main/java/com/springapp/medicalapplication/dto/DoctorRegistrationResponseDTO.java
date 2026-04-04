package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.RequestStatus;
import java.time.LocalDateTime;

public class DoctorRegistrationResponseDTO {
    public Long id;
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public String licenseNumber;
    public String phone;
    public String cabinetAddress;

    public RequestStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime reviewedAt;
    public String reviewReason;
}
