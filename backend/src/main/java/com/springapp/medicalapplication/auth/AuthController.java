package com.springapp.medicalapplication.auth;

import com.springapp.medicalapplication.auth.dto.LoginRequest;
import com.springapp.medicalapplication.auth.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test() {
        return "AUTH CONTROLLER OK - Public endpoint";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
         LoginResponse response = authService.login(request);
         return ResponseEntity.ok(response);

}



    }
