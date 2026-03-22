package com.studentmanagement.student.controller;

import com.studentmanagement.common.dto.ApiResponse;
import com.studentmanagement.student.dto.StudentCreateRequest;
import com.studentmanagement.student.dto.StudentDto;
import com.studentmanagement.student.dto.StudentUpdateRequest;
import com.studentmanagement.student.entity.Student;
import com.studentmanagement.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    
    private final StudentService studentService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAllStudents() {
        log.info("GET /api/students - Getting all students");
        
        List<StudentDto> students = studentService.getAllStudents();
        ApiResponse<List<StudentDto>> response = ApiResponse.success(students, "Students retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(@PathVariable Long id) {
        log.info("GET /api/students/{}", id);
        
        StudentDto student = studentService.getStudentById(id);
        ApiResponse<StudentDto> response = ApiResponse.success(student, "Student retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentByEmail(@PathVariable String email) {
        log.info("GET /api/students/email/{}", email);
        
        StudentDto student = studentService.getStudentByEmail(email);
        ApiResponse<StudentDto> response = ApiResponse.success(student, "Student retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDto>> createStudent(@Valid @RequestBody StudentCreateRequest request) {
        log.info("POST /api/students - Creating student with email: {}", request.getEmail());
        
        StudentDto createdStudent = studentService.createStudent(request);
        ApiResponse<StudentDto> response = ApiResponse.success(createdStudent, "Student created successfully");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(
            @PathVariable Long id, 
            @Valid @RequestBody StudentUpdateRequest request) {
        log.info("PUT /api/students/{}", id);
        
        StudentDto updatedStudent = studentService.updateStudent(id, request);
        ApiResponse<StudentDto> response = ApiResponse.success(updatedStudent, "Student updated successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.info("DELETE /api/students/{}", id);
        
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getStudentsByStatus(@PathVariable Student.StudentStatus status) {
        log.info("GET /api/students/status/{}", status);
        
        List<StudentDto> students = studentService.getStudentsByStatus(status);
        ApiResponse<List<StudentDto>> response = ApiResponse.success(students, "Students retrieved by status successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentDto>>> searchStudents(@RequestParam String keyword) {
        log.info("GET /api/students/search - keyword: {}", keyword);
        
        List<StudentDto> students = studentService.searchStudents(keyword);
        ApiResponse<List<StudentDto>> response = ApiResponse.success(students, "Students searched successfully");
        
        return ResponseEntity.ok(response);
    }
}
