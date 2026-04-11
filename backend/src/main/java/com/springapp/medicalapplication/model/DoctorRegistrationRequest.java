package com.springapp.medicalapplication.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_registration_requests",
        uniqueConstraints = {
                @UniqueConstraint(name="uk_doc_req_email", columnNames = "email"),
                @UniqueConstraint(name="uk_doc_req_username", columnNames = "email"),
                @UniqueConstraint(name="uk_doc_req_license", columnNames = "license_number")
        })
public class DoctorRegistrationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // date cont
    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 120)
    private String email;

    // password HASHED
    @Column(nullable = false)
    private String passwordHash;

    // date doctor
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "license_number", nullable = false, length = 50)
    private String licenseNumber;

    @Column(length = 20)
    private String phone;

    @Column(name = "cabinet_address", columnDefinition = "TEXT")
    private String cabinetAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "review_reason", columnDefinition = "TEXT")
    private String reviewReason;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    // getters/setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCabinetAddress() {
        return cabinetAddress;
    }

    public void setCabinetAddress(String cabinetAddress) {
        this.cabinetAddress = cabinetAddress;
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
