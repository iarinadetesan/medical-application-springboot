package com.springapp.medicalapplication.patient.dto;

import com.springapp.medicalapplication.model.Gender;
import java.time.LocalDate;

public class PatientRequestDTO {
    public Long userId;
    public String firstName;
    public String lastName;
    public String cnp;
    public LocalDate dateOfBirth;
    public Gender gender;
    public String phone;
    public String address;
    public Long familyDoctorId; // optional
}
