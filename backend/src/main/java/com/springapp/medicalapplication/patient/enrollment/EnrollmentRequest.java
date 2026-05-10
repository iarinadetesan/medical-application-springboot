package com.springapp.medicalapplication.patient.enrollment;

import com.springapp.medicalapplication.doctor.Doctor;
import com.springapp.medicalapplication.common.RequestStatus;
import com.springapp.medicalapplication.patient.Patient;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollment_requests",
        uniqueConstraints = {
                @UniqueConstraint(name="uk_enroll_patient_doctor", columnNames = {"patient_id","doctor_id"})
        })
public class EnrollmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name="doctor_id", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name="reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name="review_reason", columnDefinition = "TEXT")
    private String reviewReason;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); }

    // getters/setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
    }
}
