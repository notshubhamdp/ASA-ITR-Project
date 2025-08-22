package com.attendence.Student.Attendence.repository;

import com.attendence.Student.Attendence.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    Student findByUsername(String username);
    java.util.List<Student> findByApproved(boolean approved);
}
