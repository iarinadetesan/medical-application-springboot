package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.Consultation;

public class ConsultationMapper {

    public static ConsultationResponseDTO toDto(Consultation c) {
        ConsultationResponseDTO dto = new ConsultationResponseDTO();
        dto.id = c.getId();

        if (c.getPatient() != null) {
            dto.patientId = c.getPatient().getId();
            dto.patientName = c.getPatient().getFullName();
        }

        if (c.getDoctor() != null) {
            dto.doctorId = c.getDoctor().getId();
            dto.doctorName = c.getDoctor().getFullName();
        }

        dto.consultationDate = c.getConsultationDate();
        dto.diagnosis = c.getDiagnosis();
        dto.notes = c.getNotes();
        dto.createdAt = c.getCreatedAt();

        return dto;
    }
}


