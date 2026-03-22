package com.studentmanagement.enrollment.client;

import com.studentmanagement.common.dto.ApiResponse;
import com.studentmanagement.enrollment.dto.StudentDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service", url = "${student.service.url:http://localhost:8081}")
public interface StudentServiceClient {
    
    @GetMapping("/api/students/{id}")
    @CircuitBreaker(name = "studentService", fallbackMethod = "getStudentFallback")
    @Retry(name = "studentService")
    ApiResponse<StudentDto> getStudentById(@PathVariable("id") Long id);
    
    @GetMapping("/api/students/email/{email}")
    @CircuitBreaker(name = "studentService", fallbackMethod = "getStudentByEmailFallback")
    @Retry(name = "studentService")
    ApiResponse<StudentDto> getStudentByEmail(@PathVariable("email") String email);
    
    default ApiResponse<StudentDto> getStudentFallback(Long id, Exception ex) {
        StudentDto fallbackStudent = StudentDto.builder()
                .id(id)
                .firstName("Service")
                .lastName("Unavailable")
                .email("service@unavailable.com")
                .phoneNumber("0000000000")
                .status(com.studentmanagement.enrollment.dto.Student.StudentStatus.INACTIVE)
                .build();
        
        return ApiResponse.success(fallbackStudent, "Student service is currently unavailable. Showing fallback data.");
    }
    
    default ApiResponse<StudentDto> getStudentByEmailFallback(String email, Exception ex) {
        StudentDto fallbackStudent = StudentDto.builder()
                .firstName("Service")
                .lastName("Unavailable")
                .email(email)
                .phoneNumber("0000000000")
                .status(com.studentmanagement.enrollment.dto.Student.StudentStatus.INACTIVE)
                .build();
        
        return ApiResponse.success(fallbackStudent, "Student service is currently unavailable. Showing fallback data.");
    }
}
