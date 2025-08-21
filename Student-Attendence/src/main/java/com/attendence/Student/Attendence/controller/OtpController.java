package com.attendence.Student.Attendence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Update the import path below to match the actual location of OtpService
// For example, if OtpService is in com.attendence.Student.Attendence.services, use:
import com.attendence.Student.Attendence.services.OtpService;


import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;

    @GetMapping("/get-otp")
    public String getOtpPage() {
        return "getOtp.jsp"; // JSP page to enter email
    }


    @PostMapping(value = "/send-otp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestParam("email") String email) {
        Map<String, Object> response = new HashMap<>();
        try {
            otpService.sendOtpEmail(email);
            response.put("success", true);
            response.put("message", "OTP sent to your email!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to send OTP: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/verify-otp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestParam("otp") String otp) {
        Map<String, Object> response = new HashMap<>();
        if (otpService.validateOtp(otp)) {
            response.put("success", true);
            response.put("message", "OTP Verified Successfully!");
        } else {
            response.put("success", false);
            response.put("message", "Invalid OTP, try again.");
        }
        return ResponseEntity.ok(response);
    }
}
