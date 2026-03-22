package com.studentmanagement.course.controller;

import com.studentmanagement.common.dto.ApiResponse;
import com.studentmanagement.course.dto.CourseCreateRequest;
import com.studentmanagement.course.dto.CourseDto;
import com.studentmanagement.course.dto.CourseUpdateRequest;
import com.studentmanagement.course.entity.Course;
import com.studentmanagement.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    
    private final CourseService courseService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseDto>>> getAllCourses() {
        log.info("GET /api/courses - Getting all courses");
        
        List<CourseDto> courses = courseService.getAllCourses();
        ApiResponse<List<CourseDto>> response = ApiResponse.success(courses, "Courses retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable Long id) {
        log.info("GET /api/courses/{}", id);
        
        CourseDto course = courseService.getCourseById(id);
        ApiResponse<CourseDto> response = ApiResponse.success(course, "Course retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/code/{courseCode}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseByCode(@PathVariable String courseCode) {
        log.info("GET /api/courses/code/{}", courseCode);
        
        CourseDto course = courseService.getCourseByCode(courseCode);
        ApiResponse<CourseDto> response = ApiResponse.success(course, "Course retrieved successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        log.info("POST /api/courses - Creating course with code: {}", request.getCourseCode());
        
        CourseDto createdCourse = courseService.createCourse(request);
        ApiResponse<CourseDto> response = ApiResponse.success(createdCourse, "Course created successfully");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(
            @PathVariable Long id, 
            @Valid @RequestBody CourseUpdateRequest request) {
        log.info("PUT /api/courses/{}", id);
        
        CourseDto updatedCourse = courseService.updateCourse(id, request);
        ApiResponse<CourseDto> response = ApiResponse.success(updatedCourse, "Course updated successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.info("DELETE /api/courses/{}", id);
        
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<CourseDto>>> getCoursesByDepartment(@PathVariable String department) {
        log.info("GET /api/courses/department/{}", department);
        
        List<CourseDto> courses = courseService.getCoursesByDepartment(department);
        ApiResponse<List<CourseDto>> response = ApiResponse.success(courses, "Courses retrieved by department successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<CourseDto>>> getCoursesByStatus(@PathVariable Course.CourseStatus status) {
        log.info("GET /api/courses/status/{}", status);
        
        List<CourseDto> courses = courseService.getCoursesByStatus(status);
        ApiResponse<List<CourseDto>> response = ApiResponse.success(courses, "Courses retrieved by status successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CourseDto>>> searchCourses(@RequestParam String keyword) {
        log.info("GET /api/courses/search - keyword: {}", keyword);
        
        List<CourseDto> courses = courseService.searchCourses(keyword);
        ApiResponse<List<CourseDto>> response = ApiResponse.success(courses, "Courses searched successfully");
        
        return ResponseEntity.ok(response);
    }
}
