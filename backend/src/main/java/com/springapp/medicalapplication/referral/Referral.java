package com.springapp.medicalapplication.referral;

import com.springapp.medicalapplication.doctor.Doctor;
import com.springapp.medicalapplication.patient.Patient;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "referrals")
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "referring_doctor_id", nullable = false)
    private Doctor referringDoctor;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReferralStatus status = ReferralStatus.PENDING;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public Referral() {}

    public Referral(Patient patient, Doctor referringDoctor, String specialization,
                    String reason, LocalDate issueDate) {
        this.patient = patient;
        this.referringDoctor = referringDoctor;
        this.specialization = specialization;
        this.reason = reason;
        this.issueDate = issueDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getReferringDoctor() { return referringDoctor; }
    public void setReferringDoctor(Doctor referringDoctor) { this.referringDoctor = referringDoctor; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public ReferralStatus getStatus() { return status; }
    public void setStatus(ReferralStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}