package com.studentmanagement.student.mapper;

import com.studentmanagement.student.dto.StudentCreateRequest;
import com.studentmanagement.student.dto.StudentDto;
import com.studentmanagement.student.entity.Student;
import org.springframework.stereotype.Component;

/**
 * Simple Student Mapper - Basic entity/DTO conversions
 * Handles mapping between Student entity and DTOs
 */
@Component
public class StudentMapper {
    
    /**
     * Convert Student entity to StudentDto
     * @param student - Student entity
     * @return StudentDto
     */
    public StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }
        
        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .status(student.getStatus())
                .build();
    }
    
    /**
     * Convert StudentDto to Student entity
     * @param studentDto - StudentDto
     * @return Student entity
     */
    public Student toEntity(StudentDto studentDto) {
        if (studentDto == null) {
            return null;
        }
        
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setStatus(studentDto.getStatus());
        
        return student;
    }
    
    /**
     * Convert StudentCreateRequest to Student entity
     * @param request - StudentCreateRequest
     * @return Student entity
     */
    public Student toEntity(StudentCreateRequest request) {
        if (request == null) {
            return null;
        }
        
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setStatus(request.getStatus());
        
        return student;
    }
}
