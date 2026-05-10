package com.springapp.medicalapplication.patient.registration;

import com.springapp.medicalapplication.common.Gender;
import java.time.LocalDate;

public class RegisterPatientRequest {
    public String username;
    public String email;
    public String password;

    public String firstName;
    public String lastName;
    public String cnp;
    public LocalDate dateOfBirth;
    public Gender gender;
    public String phone;
    public String address;
}
