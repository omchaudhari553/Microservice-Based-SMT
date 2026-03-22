package com.studentmanagement.enrollment.dto;

import com.studentmanagement.enrollment.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDateTime dateOfBirth;
    private Student.Gender gender;
    private LocalDateTime enrollmentDate;
    private LocalDateTime graduationDate;
    private Student.StudentStatus status;
    private Double gpa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    public enum StudentStatus {
        ACTIVE, INACTIVE, GRADUATED, SUSPENDED, WITHDRAWN
    }
}
