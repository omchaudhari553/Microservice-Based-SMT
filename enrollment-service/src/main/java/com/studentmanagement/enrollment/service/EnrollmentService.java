package com.studentmanagement.enrollment.service;

import com.studentmanagement.common.exception.ResourceNotFoundException;
import com.studentmanagement.enrollment.client.CourseServiceClient;
import com.studentmanagement.enrollment.client.StudentServiceClient;
import com.studentmanagement.enrollment.dto.CourseDto;
import com.studentmanagement.enrollment.dto.EnrollmentCreateRequest;
import com.studentmanagement.enrollment.dto.EnrollmentDto;
import com.studentmanagement.enrollment.dto.EnrollmentUpdateRequest;
import com.studentmanagement.enrollment.dto.StudentDto;
import com.studentmanagement.enrollment.entity.Enrollment;
import com.studentmanagement.enrollment.mapper.EnrollmentMapper;
import com.studentmanagement.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final StudentServiceClient studentServiceClient;
    private final CourseServiceClient courseServiceClient;
    
    public List<EnrollmentDto> getAllEnrollments() {
        log.info("Fetching all enrollments");
        
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream()
                .map(this::enrichEnrollmentDto)
                .collect(Collectors.toList());
    }
    
    public EnrollmentDto getEnrollmentById(Long id) {
        log.info("Fetching enrollment by id: {}", id);
        
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        
        return enrichEnrollmentDto(enrollment);
    }
    
    public EnrollmentDto createEnrollment(EnrollmentCreateRequest request) {
        log.info("Creating enrollment for student: {}, course: {}", request.getStudentId(), request.getCourseId());
        
        if (enrollmentRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())) {
            throw new RuntimeException("Student is already enrolled in this course");
        }
        
        validateStudentExists(request.getStudentId());
        validateCourseExists(request.getCourseId());
        
        Enrollment enrollment = enrollmentMapper.toEntity(request);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        log.info("Enrollment created successfully with id: {}", savedEnrollment.getId());
        
        return enrichEnrollmentDto(savedEnrollment);
    }
    
    public EnrollmentDto updateEnrollment(Long id, EnrollmentUpdateRequest request) {
        log.info("Updating enrollment with id: {}", id);
        
        Enrollment existingEnrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        
        if (request.getStatus() != null) {
            existingEnrollment.setStatus(request.getStatus());
        }
        
        Enrollment updatedEnrollment = enrollmentRepository.save(existingEnrollment);
        log.info("Enrollment updated successfully with id: {}", updatedEnrollment.getId());
        
        return enrichEnrollmentDto(updatedEnrollment);
    }
    
    public void deleteEnrollment(Long id) {
        log.info("Deleting enrollment with id: {}", id);
        
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
        
        enrollmentRepository.delete(enrollment);
        log.info("Enrollment deleted successfully with id: {}", id);
    }
    
    public List<EnrollmentDto> getEnrollmentsByStudent(Long studentId) {
        log.info("Fetching enrollments for student: {}", studentId);
        
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(this::enrichEnrollmentDto)
                .collect(Collectors.toList());
    }
    
    public List<EnrollmentDto> getEnrollmentsByCourse(Long courseId) {
        log.info("Fetching enrollments for course: {}", courseId);
        
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        return enrollments.stream()
                .map(this::enrichEnrollmentDto)
                .collect(Collectors.toList());
    }
    
    public List<EnrollmentDto> getEnrollmentsByStatus(Enrollment.EnrollmentStatus status) {
        log.info("Fetching enrollments by status: {}", status);
        
        List<Enrollment> enrollments = enrollmentRepository.findByStatus(status);
        return enrollments.stream()
                .map(this::enrichEnrollmentDto)
                .collect(Collectors.toList());
    }
    
    private EnrollmentDto enrichEnrollmentDto(Enrollment enrollment) {
        EnrollmentDto dto = enrollmentMapper.toDto(enrollment);
        
        try {
            var studentResponse = studentServiceClient.getStudentById(enrollment.getStudentId());
            if (studentResponse.isSuccess() && studentResponse.getData() != null) {
                dto.setStudent(studentResponse.getData());
            }
        } catch (Exception e) {
            log.warn("Failed to fetch student details for enrollment: {}", enrollment.getId(), e);
        }
        
        try {
            var courseResponse = courseServiceClient.getCourseById(enrollment.getCourseId());
            if (courseResponse.isSuccess() && courseResponse.getData() != null) {
                dto.setCourse(courseResponse.getData());
            }
        } catch (Exception e) {
            log.warn("Failed to fetch course details for enrollment: {}", enrollment.getId(), e);
        }
        
        return dto;
    }
    
    private void validateStudentExists(Long studentId) {
        try {
            studentServiceClient.getStudentById(studentId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
    }
    
    private void validateCourseExists(Long courseId) {
        try {
            courseServiceClient.getCourseById(courseId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }
    }
}
