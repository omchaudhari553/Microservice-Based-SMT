package com.studentmanagement.enrollment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    
    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;
    
    @Column(name = "graduation_date")
    private LocalDateTime graduationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StudentStatus status;
    
    @Column(name = "gpa")
    private Double gpa;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    public enum StudentStatus {
        ACTIVE, INACTIVE, GRADUATED, SUSPENDED, WITHDRAWN
    }
}
