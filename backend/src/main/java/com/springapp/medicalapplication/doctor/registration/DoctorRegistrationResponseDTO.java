package com.springapp.medicalapplication.doctor.registration;

import com.springapp.medicalapplication.common.RequestStatus;
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
