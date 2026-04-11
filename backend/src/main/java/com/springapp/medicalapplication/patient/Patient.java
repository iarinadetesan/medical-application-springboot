package com.springapp.medicalapplication.patient;

import com.springapp.medicalapplication.doctor.Doctor;
import com.springapp.medicalapplication.consultation.Consultation;
import com.springapp.medicalapplication.model.Gender;
import com.springapp.medicalapplication.prescription.Prescription;
import com.springapp.medicalapplication.referral.Referral;
import com.springapp.medicalapplication.user.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

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

    @Column(unique = true, nullable = false, length = 13)
    private String cnp;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @ManyToOne
    @JoinColumn(name = "family_doctor_id")
    private Doctor familyDoctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Consultation> consultations = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Referral> referrals = new ArrayList<>();

    // Constructors
    public Patient() {}

    public Patient(User user, String firstName, String lastName, String cnp,
                   LocalDate dateOfBirth, Gender gender) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
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

    public String getCnp() { return cnp; }
    public void setCnp(String cnp) { this.cnp = cnp; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Doctor getFamilyDoctor() { return familyDoctor; }
    public void setFamilyDoctor(Doctor familyDoctor) { this.familyDoctor = familyDoctor; }

    public List<Consultation> getConsultations() { return consultations; }
    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; }

    public List<Prescription> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(List<Prescription> prescriptions) { this.prescriptions = prescriptions; }

    public List<Referral> getReferrals() { return referrals; }
    public void setReferrals(List<Referral> referrals) { this.referrals = referrals; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}