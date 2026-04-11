package com.springapp.medicalapplication.referral.dto;

import com.springapp.medicalapplication.referral.Referral;

public class ReferralMapper {

    public static ReferralResponseDTO toDto(Referral r) {
        ReferralResponseDTO dto = new ReferralResponseDTO();
        dto.id = r.getId();

        if (r.getPatient() != null) {
            dto.patientId = r.getPatient().getId();
            dto.patientName = r.getPatient().getFullName();
        }

        if (r.getReferringDoctor() != null) {
            dto.referringDoctorId = r.getReferringDoctor().getId();
            dto.referringDoctorName = r.getReferringDoctor().getFullName();
        }

        dto.specialization = r.getSpecialization();
        dto.reason = r.getReason();
        dto.issueDate = r.getIssueDate();
        dto.expiryDate = r.getExpiryDate();
        dto.status = r.getStatus();
        dto.createdAt = r.getCreatedAt();

        return dto;
    }
}