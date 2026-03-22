package com.studentmanagement.enrollment.dto;

import com.studentmanagement.enrollment.entity.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentUpdateRequest {
    
    private Enrollment.EnrollmentStatus status;
    
    private LocalDateTime completionDate;
    
    private String grade;
    
    private Double attendancePercentage;
    
    private String notes;
}
