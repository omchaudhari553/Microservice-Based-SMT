package com.studentmanagement.course.dto;

import com.studentmanagement.course.entity.Course;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseUpdateRequest {
    
    @Size(min = 5, max = 100, message = "Course name must be between 5 and 100 characters")
    private String courseName;
    
    private Integer credits;
    
    private String department;
    
    private Course.CourseStatus status;
}
