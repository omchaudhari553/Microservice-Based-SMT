package com.studentmanagement.enrollment.dto;

import com.studentmanagement.enrollment.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    
    private Long id;
    private String courseCode;
    private String courseName;
    private String description;
    private Integer credits;
    private String department;
    private String semester;
    private String academicYear;
    private Integer maxStudents;
    private Integer currentEnrollment;
    private Course.CourseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum CourseStatus {
        ACTIVE, INACTIVE, FULL, CANCELLED
    }
}
