package com.studentmanagement.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    @NotBlank(message = "Course code is required")
    @Size(min = 3, max = 10, message = "Course code must be between 3 and 10 characters")
    @Column(name = "course_code", unique = true, nullable = false)
    private String courseCode;
    
    @NotBlank(message = "Course name is required")
    @Size(max = 100, message = "Course name must not exceed 100 characters")
    @Column(name = "course_name", nullable = false)
    private String courseName;
    
    @NotNull(message = "Credits are required")
    @Column(name = "credits", nullable = false)
    private Integer credits;
    
    @Size(max = 50, message = "Department name must not exceed 50 characters")
    @Column(name = "department")
    private String department;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private CourseStatus status = CourseStatus.ACTIVE;
    
    public enum CourseStatus {
        ACTIVE, INACTIVE, COMPLETED
    }
}
