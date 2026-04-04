package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorMapper {

    public static DoctorResponseDTO toDto(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.id = doctor.getId();

        if (doctor.getUser() != null) {
            dto.userId = doctor.getUser().getId();
            dto.username = doctor.getUser().getUsername();
            dto.email = doctor.getUser().getEmail();
        }

        dto.firstName = doctor.getFirstName();
        dto.lastName = doctor.getLastName();
        dto.licenseNumber=doctor.getLicenseNumber();

        dto.phone=doctor.getPhone();
        dto.cabinetAddress=doctor.getCabinetAddress();

      /**  if (doctor.getPatients() != null) {
            dto.patients = doctor.getPatients();
        }
*/
        return dto;
    }

}
