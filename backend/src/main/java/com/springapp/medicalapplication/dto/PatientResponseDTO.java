package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.Gender;
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

    public Long familyDoctorId; // doar id, ca să evitam loop
}
