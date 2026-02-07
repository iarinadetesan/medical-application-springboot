package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.Prescription;

public class PrescriptionMapper {

    public static PrescriptionResponseDTO toDto(Prescription p) {
        PrescriptionResponseDTO dto = new PrescriptionResponseDTO();
        dto.id = p.getId();

        if (p.getPatient() != null) {
            dto.patientId = p.getPatient().getId();
            dto.patientName = p.getPatient().getFullName();
        }

        if (p.getDoctor() != null) {
            dto.doctorId = p.getDoctor().getId();
            dto.doctorName = p.getDoctor().getFullName();
        }

        if (p.getConsultation() != null) {
            dto.consultationId = p.getConsultation().getId();
        }

        dto.medicationName = p.getMedicationName();
        dto.dosage = p.getDosage();
        dto.instructions = p.getInstructions();
        dto.issueDate = p.getIssueDate();
        dto.expiryDate = p.getExpiryDate();
        dto.status = p.getStatus();
        dto.createdAt = p.getCreatedAt();

        return dto;
    }
}