package com.studentmanagement.student.repository;

import com.studentmanagement.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Simple Student Repository - Basic database operations
 * Handles student data access without pagination
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    /**
     * Find student by email
     * @param email - Student email
     * @return Optional student
     */
    Optional<Student> findByEmail(String email);
    
    /**
     * Check if email exists
     * @param email - Student email
     * @return true if exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Find students by status
     * @param status - Student status
     * @return List of students
     */
    List<Student> findByStatus(Student.StudentStatus status);
    
    /**
     * Search students by keyword (first name or last name)
     * @param keyword - Search term
     * @return List of matching students
     */
    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:keyword% OR s.lastName LIKE %:keyword%")
    List<Student> searchStudents(@Param("keyword") String keyword);
}
