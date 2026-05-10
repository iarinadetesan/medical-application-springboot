package com.springapp.medicalapplication.patient.enrollment;

import com.springapp.medicalapplication.common.RequestStatus;
import java.time.LocalDateTime;

public class EnrollmentResponseDTO {
    public Long id;
    public Long patientId;
    public Long doctorId;

    public String patientFirstName;
    public String patientLastName;
    public String patientFullName;
    public String patientEmail;
    public String patientPhone;
    public String patientAddress;

    public RequestStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime reviewedAt;
    public String reviewReason;
}

