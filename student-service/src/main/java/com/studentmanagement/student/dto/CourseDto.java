package com.studentmanagement.student.dto;

import com.studentmanagement.student.entity.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    
    @NotBlank(message = "Course code is required")
    @Size(min = 3, max = 10, message = "Course code must be between 3 and 10 characters")
    private String courseCode;
    
    @NotBlank(message = "Course name is required")
    @Size(min = 5, max = 100, message = "Course name must be between 5 and 100 characters")
    private String courseName;
    
    private String description;
    
    @NotNull(message = "Credits are required")
    private Integer credits;
    
    private String department;
    
    private String semester;
    
    private String academicYear;
    
    private Integer maxStudents;
    
    private Integer currentEnrollment;
    
    private Course.CourseStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
