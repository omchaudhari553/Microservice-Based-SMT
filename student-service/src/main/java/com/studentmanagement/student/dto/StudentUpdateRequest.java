package com.studentmanagement.student.dto;

import com.studentmanagement.student.entity.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple Student Update Request - Core fields only
 * Used to update existing students
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {
    
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private Student.StudentStatus status;
}
