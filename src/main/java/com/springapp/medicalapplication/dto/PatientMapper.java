package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.Patient;

public class PatientMapper {

    public static PatientResponseDTO toDto(Patient p) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.id = p.getId();

        if (p.getUser() != null) {
            dto.userId = p.getUser().getId();
            dto.username = p.getUser().getUsername();
            dto.email = p.getUser().getEmail();
        }

        dto.firstName = p.getFirstName();
        dto.lastName = p.getLastName();
        dto.cnp = p.getCnp();
        dto.dateOfBirth = p.getDateOfBirth();
        dto.gender = p.getGender();
        dto.phone = p.getPhone();
        dto.address = p.getAddress();

        if (p.getFamilyDoctor() != null) {
            dto.familyDoctorId = p.getFamilyDoctor().getId();
        }

        return dto;
    }
}
