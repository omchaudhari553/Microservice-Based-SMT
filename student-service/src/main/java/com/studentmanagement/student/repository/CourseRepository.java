package com.studentmanagement.student.repository;

import com.studentmanagement.student.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Optional<Course> findByCourseCode(String courseCode);
    
    boolean existsByCourseCode(String courseCode);
    
    boolean existsByCourseCodeAndIdNot(String courseCode, Long id);
    
    List<Course> findByDepartment(String department);
    
    List<Course> findByStatus(Course.CourseStatus status);
    
    @Query("SELECT c FROM Course c WHERE c.currentEnrollment < c.maxStudents")
    List<Course> findAvailableCourses();
    
    @Query("SELECT COUNT(c) FROM Course c WHERE c.department = :department")
    long countByDepartment(@Param("department") String department);
    
    @Query("SELECT c FROM Course c WHERE c.credits BETWEEN :minCredits AND :maxCredits")
    List<Course> findByCreditsRange(@Param("minCredits") Integer minCredits, @Param("maxCredits") Integer maxCredits);
    
    @Query("SELECT c FROM Course c WHERE c.semester = :semester AND c.academicYear = :academicYear")
    List<Course> findBySemesterAndAcademicYear(@Param("semester") String semester, @Param("academicYear") String academicYear);
}
