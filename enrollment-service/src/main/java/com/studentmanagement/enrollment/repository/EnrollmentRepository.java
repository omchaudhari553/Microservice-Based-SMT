package com.studentmanagement.enrollment.repository;

import com.studentmanagement.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Simple Enrollment Repository - Basic database operations
 * Handles enrollment data access without pagination
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    /**
     * Find enrollment by student and course
     * @param studentId - Student ID
     * @param courseId - Course ID
     * @return Optional enrollment
     */
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
    
    /**
     * Check if enrollment exists by student and course
     * @param studentId - Student ID
     * @param courseId - Course ID
     * @return true if exists
     */
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    
    /**
     * Find enrollments by student ID
     * @param studentId - Student ID
     * @return List of enrollments
     */
    List<Enrollment> findByStudentId(Long studentId);
    
    /**
     * Find enrollments by course ID
     * @param courseId - Course ID
     * @return List of enrollments
     */
    List<Enrollment> findByCourseId(Long courseId);
    
    /**
     * Find enrollments by status
     * @param status - Enrollment status
     * @return List of enrollments
     */
    List<Enrollment> findByStatus(Enrollment.EnrollmentStatus status);
}
