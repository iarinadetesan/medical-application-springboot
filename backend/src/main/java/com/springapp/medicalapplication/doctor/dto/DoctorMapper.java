package com.springapp.medicalapplication.doctor.dto;

import com.springapp.medicalapplication.doctor.Doctor;

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
