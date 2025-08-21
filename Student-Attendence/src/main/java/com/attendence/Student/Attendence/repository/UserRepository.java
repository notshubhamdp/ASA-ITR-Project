package com.attendence.Student.Attendence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.attendence.Student.Attendence.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // This interface will automatically provide CRUD operations for User entity
    // No need to define methods unless you want custom queries

}
