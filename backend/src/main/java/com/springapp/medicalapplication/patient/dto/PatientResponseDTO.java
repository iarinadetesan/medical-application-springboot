package com.springapp.medicalapplication.patient.dto;

import com.springapp.medicalapplication.common.Gender;
import java.time.LocalDate;

public class PatientResponseDTO {
    public Long id;

    public Long userId;
    public String username;
    public String email;

    public String firstName;
    public String lastName;
    public String cnp;
    public LocalDate dateOfBirth;
    public Gender gender;
    public String phone;
    public String address;

    public Long familyDoctorId;
    public String familyDoctorName;
    public String familyDoctorFirstName;
    public String familyDoctorLastName;
}
