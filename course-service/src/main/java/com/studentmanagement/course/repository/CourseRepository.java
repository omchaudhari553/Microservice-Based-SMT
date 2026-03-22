package com.studentmanagement.course.repository;

import com.studentmanagement.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Simple Course Repository - Basic database operations
 * Handles course data access without pagination
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    /**
     * Find course by unique course code
     * @param courseCode - Course code
     * @return Optional course
     */
    Optional<Course> findByCourseCode(String courseCode);
    
    /**
     * Check if course code exists
     * @param courseCode - Course code
     * @return true if exists
     */
    boolean existsByCourseCode(String courseCode);
    
    /**
     * Find courses by department
     * @param department - Department name
     * @return List of courses
     */
    List<Course> findByDepartment(String department);
    
    /**
     * Find courses by status
     * @param status - Course status
     * @return List of courses
     */
    List<Course> findByStatus(Course.CourseStatus status);
    
    /**
     * Search courses by keyword (name or code)
     * @param keyword - Search term
     * @return List of matching courses
     */
    @Query("SELECT c FROM Course c WHERE c.courseName LIKE %:keyword% OR c.courseCode LIKE %:keyword%")
    List<Course> searchCourses(@Param("keyword") String keyword);
}
