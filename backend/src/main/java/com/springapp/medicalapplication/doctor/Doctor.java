
// ==================== Doctor.java ====================
package com.springapp.medicalapplication.doctor;

import com.springapp.medicalapplication.patient.Patient;
import com.springapp.medicalapplication.user.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "license_number", unique = true, nullable = false, length = 50)
    private String licenseNumber;

    @Column(length = 20)
    private String phone;

    @Column(name = "cabinet_address", columnDefinition = "TEXT")
    private String cabinetAddress;

    @OneToMany(mappedBy = "familyDoctor")
    private List<Patient> patients = new ArrayList<>();

    // Constructors
    public Doctor() {}

    public Doctor(User user, String firstName, String lastName, String licenseNumber) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCabinetAddress() { return cabinetAddress; }
    public void setCabinetAddress(String cabinetAddress) { this.cabinetAddress = cabinetAddress; }

    public List<Patient> getPatients() { return patients; }
    public void setPatients(List<Patient> patients) { this.patients = patients; }

    public String getFullName() {
        return "Dr. " + firstName + " " + lastName;
    }
}