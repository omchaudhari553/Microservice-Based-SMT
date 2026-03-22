package com.studentmanagement.enrollment.mapper;

import com.studentmanagement.enrollment.dto.EnrollmentCreateRequest;
import com.studentmanagement.enrollment.dto.EnrollmentDto;
import com.studentmanagement.enrollment.entity.Enrollment;
import org.springframework.stereotype.Component;

/**
 * Simple Enrollment Mapper - Basic entity/DTO conversions
 * Handles mapping between Enrollment entity and DTOs
 */
@Component
public class EnrollmentMapper {
    
    /**
     * Convert Enrollment entity to EnrollmentDto
     * @param enrollment - Enrollment entity
     * @return EnrollmentDto
     */
    public EnrollmentDto toDto(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }
        
        return EnrollmentDto.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudentId())
                .courseId(enrollment.getCourseId())
                .status(enrollment.getStatus())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .build();
    }
    
    /**
     * Convert EnrollmentDto to Enrollment entity
     * @param enrollmentDto - EnrollmentDto
     * @return Enrollment entity
     */
    public Enrollment toEntity(EnrollmentDto enrollmentDto) {
        if (enrollmentDto == null) {
            return null;
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentDto.getId());
        enrollment.setStudentId(enrollmentDto.getStudentId());
        enrollment.setCourseId(enrollmentDto.getCourseId());
        enrollment.setStatus(enrollmentDto.getStatus());
        enrollment.setEnrollmentDate(enrollmentDto.getEnrollmentDate());
        
        return enrollment;
    }
    
    /**
     * Convert EnrollmentCreateRequest to Enrollment entity
     * @param request - EnrollmentCreateRequest
     * @return Enrollment entity
     */
    public Enrollment toEntity(EnrollmentCreateRequest request) {
        if (request == null) {
            return null;
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(request.getStudentId());
        enrollment.setCourseId(request.getCourseId());
        enrollment.setStatus(request.getStatus());
        
        return enrollment;
    }
}
