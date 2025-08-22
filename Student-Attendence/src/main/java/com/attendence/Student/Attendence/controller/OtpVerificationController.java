package com.attendence.Student.Attendence.controller;

import com.attendence.Student.Attendence.entity.Student;
import com.attendence.Student.Attendence.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OtpVerificationController {
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(@RequestParam String otp, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String sentOtp = (String) session.getAttribute("sentOtp");
        Map<String, String> registrationData = (Map<String, String>) session.getAttribute("registrationData");
        if (sentOtp != null && sentOtp.equals(otp) && registrationData != null) {
            if (studentRepository.existsByEmail(registrationData.get("email"))) {
                result.put("success", false);
                result.put("message", "Email already registered.");
                return result;
            }
            Student student = new Student();
            student.setUsername(registrationData.get("username"));
            student.setFirstname(registrationData.get("firstname"));
            student.setLastname(registrationData.get("lastname"));
            student.setEmail(registrationData.get("email"));
            student.setPhone(registrationData.get("phone"));
            student.setGender(registrationData.get("gender"));
            student.setPassword(registrationData.get("password")); // Hash in production!
            student.setApproved(false);
            studentRepository.save(student);
            session.removeAttribute("sentOtp");
            session.removeAttribute("registrationData");
            result.put("success", true);
            result.put("message", "Registration successful. Await admin approval.");
        } else {
            result.put("success", false);
            result.put("message", "Invalid OTP or session expired.");
        }
        return result;
    }
}
