package com.springapp.medicalapplication.dto;

public class LoginResponse {
    public String token;
    public String username;
    public String email;
    public String role;
    public Long userId;
    public Long profileId; // ID-ul Patient sau Doctor

    public LoginResponse(String token, String username, String email,
                         String role, Long userId, Long profileId) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
        this.userId = userId;
        this.profileId = profileId;
    }
}
