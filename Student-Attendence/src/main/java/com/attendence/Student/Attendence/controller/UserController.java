package com.attendence.Student.Attendence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.attendence.Student.Attendence.entity.Student;
import com.attendence.Student.Attendence.repository.StudentRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get current user profile (basic info)
    @GetMapping("/profile")
    public Student getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return null;
        return studentRepository.findByUsername(userDetails.getUsername());
    }

    // Update user profile (except password)
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Student updated) {
        if (userDetails == null) return ResponseEntity.status(401).body("Unauthorized");
        Student student = studentRepository.findByUsername(userDetails.getUsername());
        if (student == null) return ResponseEntity.notFound().build();
        student.setFirstname(updated.getFirstname());
        student.setLastname(updated.getLastname());
        student.setEmail(updated.getEmail());
        student.setPhone(updated.getPhone());
        student.setGender(updated.getGender());
        studentRepository.save(student);
        return ResponseEntity.ok(student);
    }

    // Change password
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String oldPassword, @RequestParam String newPassword) {
        if (userDetails == null) return ResponseEntity.status(401).body("Unauthorized");
        Student student = studentRepository.findByUsername(userDetails.getUsername());
        if (student == null) return ResponseEntity.notFound().build();
        if (!passwordEncoder.matches(oldPassword, student.getPassword())) {
            return ResponseEntity.badRequest().body("Old password is incorrect");
        }
        student.setPassword(passwordEncoder.encode(newPassword));
        studentRepository.save(student);
        return ResponseEntity.ok("Password changed successfully");
    }

    // Add more user-specific endpoints as needed
}
