package com.studentmanagement.course.service;

import com.studentmanagement.common.exception.ResourceNotFoundException;
import com.studentmanagement.course.dto.CourseCreateRequest;
import com.studentmanagement.course.dto.CourseDto;
import com.studentmanagement.course.dto.CourseUpdateRequest;
import com.studentmanagement.course.entity.Course;
import com.studentmanagement.course.mapper.CourseMapper;
import com.studentmanagement.course.repository.CourseRepository;
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
public class CourseService {
    
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    
    public List<CourseDto> getAllCourses() {
        log.info("Fetching all courses");
        
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public CourseDto getCourseById(Long id) {
        log.info("Fetching course by ID: {}", id);
        
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        
        return courseMapper.toDto(course);
    }
    
    public CourseDto getCourseByCode(String courseCode) {
        log.info("Fetching course by code: {}", courseCode);
        
        Course course = courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with code: " + courseCode));
        
        return courseMapper.toDto(course);
    }
    
    public CourseDto createCourse(CourseCreateRequest request) {
        log.info("Creating course with code: {}", request.getCourseCode());
        
        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new RuntimeException("Course with code " + request.getCourseCode() + " already exists");
        }
        
        Course course = courseMapper.toEntity(request);
        Course savedCourse = courseRepository.save(course);
        
        return courseMapper.toDto(savedCourse);
    }
    
    public CourseDto updateCourse(Long id, CourseUpdateRequest request) {
        log.info("Updating course with ID: {}", id);
        
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        
        if (request.getCourseName() != null) {
            existingCourse.setCourseName(request.getCourseName());
        }
        if (request.getCredits() != null) {
            existingCourse.setCredits(request.getCredits());
        }
        if (request.getDepartment() != null) {
            existingCourse.setDepartment(request.getDepartment());
        }
        if (request.getStatus() != null) {
            existingCourse.setStatus(request.getStatus());
        }
        
        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toDto(updatedCourse);
    }
    
    public void deleteCourse(Long id) {
        log.info("Deleting course with ID: {}", id);
        
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        
        courseRepository.delete(course);
    }
    
    public List<CourseDto> getCoursesByDepartment(String department) {
        log.info("Fetching courses by department: {}", department);
        
        List<Course> courses = courseRepository.findByDepartment(department);
        return courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<CourseDto> getCoursesByStatus(Course.CourseStatus status) {
        log.info("Fetching courses by status: {}", status);
        
        List<Course> courses = courseRepository.findByStatus(status);
        return courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<CourseDto> searchCourses(String keyword) {
        log.info("Searching courses with keyword: {}", keyword);
        
        List<Course> courses = courseRepository.searchCourses(keyword);
        return courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }
}
