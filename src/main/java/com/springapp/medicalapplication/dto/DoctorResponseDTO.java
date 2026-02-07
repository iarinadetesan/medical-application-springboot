package com.springapp.medicalapplication.dto;

import com.springapp.medicalapplication.model.Patient;
import com.springapp.medicalapplication.model.User;
import java.util.ArrayList;
import java.util.List;

public class DoctorResponseDTO {

    public Long userId;
    public String username;
    public String email;

    public Long id;
    public User user;

    public String firstName;

    public String lastName;

    public String licenseNumber;

    public String phone;
    public String cabinetAddress;
   // public List<Patient> patients = new ArrayList<>();

}
