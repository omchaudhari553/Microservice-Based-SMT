package com.studentmanagement.enrollment.controller;

import com.studentmanagement.common.dto.ApiResponse;
import com.studentmanagement.enrollment.dto.EnrollmentCreateRequest;
import com.studentmanagement.enrollment.dto.EnrollmentDto;
import com.studentmanagement.enrollment.dto.EnrollmentUpdateRequest;
import com.studentmanagement.enrollment.entity.Enrollment;
import com.studentmanagement.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {
    
    private final EnrollmentService enrollmentService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<EnrollmentDto>>> getAllEnrollments() {
        log.info("GET /api/enrollments - Getting all enrollments");
        
        List<EnrollmentDto> enrollments = enrollmentService.getAllEnrollments();
        ApiResponse<List<EnrollmentDto>> response = ApiResponse.success(enrollments, "Enrollments retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentDto>> getEnrollmentById(@PathVariable Long id) {
        log.info("GET /api/enrollments/{}", id);
        
        EnrollmentDto enrollment = enrollmentService.getEnrollmentById(id);
        ApiResponse<EnrollmentDto> response = ApiResponse.success(enrollment, "Enrollment retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentDto>> createEnrollment(@Valid @RequestBody EnrollmentCreateRequest request) {
        log.info("POST /api/enrollments - Creating enrollment for student: {}, course: {}", request.getStudentId(), request.getCourseId());
        
        EnrollmentDto createdEnrollment = enrollmentService.createEnrollment(request);
        ApiResponse<EnrollmentDto> response = ApiResponse.success(createdEnrollment, "Enrollment created successfully");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentDto>> updateEnrollment(
            @PathVariable Long id, 
            @Valid @RequestBody EnrollmentUpdateRequest request) {
        log.info("PUT /api/enrollments/{}", id);
        
        EnrollmentDto updatedEnrollment = enrollmentService.updateEnrollment(id, request);
        ApiResponse<EnrollmentDto> response = ApiResponse.success(updatedEnrollment, "Enrollment updated successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        log.info("DELETE /api/enrollments/{}", id);
        
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<EnrollmentDto>>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        log.info("GET /api/enrollments/student/{}", studentId);
        
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        ApiResponse<List<EnrollmentDto>> response = ApiResponse.success(enrollments, "Enrollments retrieved by student successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<EnrollmentDto>>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        log.info("GET /api/enrollments/course/{}", courseId);
        
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        ApiResponse<List<EnrollmentDto>> response = ApiResponse.success(enrollments, "Enrollments retrieved by course successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<EnrollmentDto>>> getEnrollmentsByStatus(@PathVariable Enrollment.EnrollmentStatus status) {
        log.info("GET /api/enrollments/status/{}", status);
        
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollmentsByStatus(status);
        ApiResponse<List<EnrollmentDto>> response = ApiResponse.success(enrollments, "Enrollments retrieved by status successfully");
        
        return ResponseEntity.ok(response);
    }
}
