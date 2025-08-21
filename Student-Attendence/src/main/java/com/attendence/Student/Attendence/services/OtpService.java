package com.attendence.Student.Attendence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    private String otp;  // store OTP temporarily

    public String generateOtp() {
        Random random = new Random();
        otp = String.format("%06d", random.nextInt(999999));
        return otp;
    }

    public void sendOtpEmail(String toEmail) {
        String otp = generateOtp();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        mailSender.send(message);
    }

    public boolean validateOtp(String inputOtp) {
        return inputOtp.equals(otp);
    }
}
