package com.studentmanagement.student.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @Size(min = 5, max = 100, message = "Course name must be between 5 and 100 characters")
    @Column(name = "course_name", nullable = false)
    private String courseName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Credits are required")
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
    
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = CourseStatus.ACTIVE;
        }
        if (currentEnrollment == null) {
            currentEnrollment = 0;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum CourseStatus {
        ACTIVE, INACTIVE, FULL, CANCELLED
    }
}
