package com.studentmanagement.student.service;

import com.studentmanagement.common.exception.ResourceNotFoundException;
import com.studentmanagement.student.dto.StudentCreateRequest;
import com.studentmanagement.student.dto.StudentDto;
import com.studentmanagement.student.dto.StudentUpdateRequest;
import com.studentmanagement.student.entity.Student;
import com.studentmanagement.student.mapper.StudentMapper;
import com.studentmanagement.student.repository.StudentRepository;
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
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    
    public List<StudentDto> getAllStudents() {
        log.info("Fetching all students");
        
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public StudentDto getStudentById(Long id) {
        log.info("Fetching student by ID: {}", id);
        
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        
        return studentMapper.toDto(student);
    }
    
    public StudentDto getStudentByEmail(String email) {
        log.info("Fetching student by email: {}", email);
        
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));
        
        return studentMapper.toDto(student);
    }
    
    public StudentDto createStudent(StudentCreateRequest request) {
        log.info("Creating student with email: {}", request.getEmail());
        
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Student with email " + request.getEmail() + " already exists");
        }
        
        Student student = studentMapper.toEntity(request);
        Student savedStudent = studentRepository.save(student);
        
        return studentMapper.toDto(savedStudent);
    }
    
    public StudentDto updateStudent(Long id, StudentUpdateRequest request) {
        log.info("Updating student with ID: {}", id);
        
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        
        if (request.getFirstName() != null) {
            existingStudent.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            existingStudent.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            existingStudent.setEmail(request.getEmail());
        }
        if (request.getStatus() != null) {
            existingStudent.setStatus(request.getStatus());
        }
        
        Student updatedStudent = studentRepository.save(existingStudent);
        return studentMapper.toDto(updatedStudent);
    }
    
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);
        
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        
        studentRepository.delete(student);
    }
    
    public List<StudentDto> getStudentsByStatus(Student.StudentStatus status) {
        log.info("Fetching students by status: {}", status);
        
        List<Student> students = studentRepository.findByStatus(status);
        return students.stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<StudentDto> searchStudents(String keyword) {
        log.info("Searching students with keyword: {}", keyword);
        
        List<Student> students = studentRepository.searchStudents(keyword);
        return students.stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}
