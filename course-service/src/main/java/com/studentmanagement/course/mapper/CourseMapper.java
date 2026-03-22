package com.studentmanagement.course.mapper;

import com.studentmanagement.course.dto.CourseCreateRequest;
import com.studentmanagement.course.dto.CourseDto;
import com.studentmanagement.course.entity.Course;
import org.springframework.stereotype.Component;

/**
 * Simple Course Mapper - Basic entity/DTO conversions
 * Handles mapping between Course entity and DTOs
 */
@Component
public class CourseMapper {
    
    /**
     * Convert Course entity to CourseDto
     * @param course - Course entity
     * @return CourseDto
     */
    public CourseDto toDto(Course course) {
        if (course == null) {
            return null;
        }
        
        return CourseDto.builder()
                .id(course.getId())
                .courseCode(course.getCourseCode())
                .courseName(course.getCourseName())
                .credits(course.getCredits())
                .department(course.getDepartment())
                .status(course.getStatus())
                .build();
    }
    
    /**
     * Convert CourseDto to Course entity
     * @param courseDto - CourseDto
     * @return Course entity
     */
    public Course toEntity(CourseDto courseDto) {
        if (courseDto == null) {
            return null;
        }
        
        Course course = new Course();
        course.setId(courseDto.getId());
        course.setCourseCode(courseDto.getCourseCode());
        course.setCourseName(courseDto.getCourseName());
        course.setCredits(courseDto.getCredits());
        course.setDepartment(courseDto.getDepartment());
        course.setStatus(courseDto.getStatus());
        
        return course;
    }
    
    /**
     * Convert CourseCreateRequest to Course entity
     * @param request - CourseCreateRequest
     * @return Course entity
     */
    public Course toEntity(CourseCreateRequest request) {
        if (request == null) {
            return null;
        }
        
        Course course = new Course();
        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setCredits(request.getCredits());
        course.setDepartment(request.getDepartment());
        course.setStatus(request.getStatus());
        
        return course;
    }
}
