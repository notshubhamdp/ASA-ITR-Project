package com.attendence.Student.Attendence.controller;

import com.attendence.Student.Attendence.entity.Student;
import com.attendence.Student.Attendence.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
public class OtpRegistrationController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttributes) {
        // Check if email already registered
        if (studentRepository.existsByEmail(params.get("email"))) {
            redirectAttributes.addFlashAttribute("error", "Email already registered.");
            return "redirect:/register.html";
        }
        // Generate OTP
        String otp = String.valueOf(100000 + new java.util.Random().nextInt(900000));
        session.setAttribute("sentOtp", otp);
        session.setAttribute("registrationData", params);

        // Send OTP to email
        String email = params.get("email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for Registration");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);

        redirectAttributes.addFlashAttribute("email", email);
        return "redirect:/otp-verification.html";
    }
}
