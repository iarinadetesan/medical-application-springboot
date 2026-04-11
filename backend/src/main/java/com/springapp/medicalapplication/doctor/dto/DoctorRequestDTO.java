package com.springapp.medicalapplication.doctor.dto;


import com.springapp.medicalapplication.user.User;

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
