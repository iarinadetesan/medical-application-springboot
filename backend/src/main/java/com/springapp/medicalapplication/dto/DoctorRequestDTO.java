package com.springapp.medicalapplication.dto;


import com.springapp.medicalapplication.model.Gender;
import com.springapp.medicalapplication.model.Patient;
import com.springapp.medicalapplication.model.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorRequestDTO {

    public Long userId;
    public Long id;

    public User user;

    public String firstName;

    public String lastName;

    public String licenseNumber;

    public String phone;
    public String cabinetAddress;
 //   public List<Patient> patients = new ArrayList<>();

}
