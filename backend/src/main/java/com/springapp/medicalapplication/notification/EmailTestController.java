package com.springapp.medicalapplication.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*")
public class EmailTestController {
    @Autowired
    private EmailService emailService;
    @GetMapping("/mail-test")
    public String mailTest() {

        emailService.send("aplicatie.medicala.info@gmail.com", "Test MedicalApp", "Email test OK");
        return "sent";
    }

}
