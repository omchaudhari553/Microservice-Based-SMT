package com.studentmanagement.enrollment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "course_code", unique = true, nullable = false)
    private String courseCode;
    
    @Column(name = "course_name", nullable = false)
    private String courseName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "credits", nullable = false)
    private Integer credits;
    
    @Column(name = "department")
    private String department;
    
    @Column(name = "semester")
    private String semester;
    
    @Column(name = "academic_year")
    private String academicYear;
    
    @Column(name = "max_students")
    private Integer maxStudents;
    
    @Column(name = "current_enrollment")
    private Integer currentEnrollment;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CourseStatus status;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum CourseStatus {
        ACTIVE, INACTIVE, FULL, CANCELLED
    }
}
