package com.studentmanagement.enrollment.dto;

import com.studentmanagement.enrollment.entity.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Simple Enrollment DTO - Core enrollment information only
 * Used for API responses and requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
    
    private Long id;
    private Long studentId;
    private Long courseId;
    private Enrollment.EnrollmentStatus status;
    private LocalDateTime enrollmentDate;
    private CourseDto course;
    private StudentDto student;
}
