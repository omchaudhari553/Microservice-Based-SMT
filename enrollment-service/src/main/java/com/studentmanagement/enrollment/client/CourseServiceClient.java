package com.studentmanagement.enrollment.client;

import com.studentmanagement.common.dto.ApiResponse;
import com.studentmanagement.enrollment.dto.CourseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "${course.service.url:http://localhost:8082}")
public interface CourseServiceClient {
    
    @GetMapping("/api/courses/{id}")
    @CircuitBreaker(name = "courseService", fallbackMethod = "getCourseFallback")
    @Retry(name = "courseService")
    ApiResponse<CourseDto> getCourseById(@PathVariable("id") Long id);
    
    @GetMapping("/api/courses/code/{courseCode}")
    @CircuitBreaker(name = "courseService", fallbackMethod = "getCourseByCodeFallback")
    @Retry(name = "courseService")
    ApiResponse<CourseDto> getCourseByCode(@PathVariable("courseCode") String courseCode);
    
    default ApiResponse<CourseDto> getCourseFallback(Long id, Exception ex) {
        CourseDto fallbackCourse = CourseDto.builder()
                .id(id)
                .courseCode("UNAVAIL")
                .courseName("Service Unavailable")
                .credits(0)
                .status(com.studentmanagement.enrollment.dto.Course.CourseStatus.INACTIVE)
                .build();
        
        return ApiResponse.success(fallbackCourse, "Course service is currently unavailable. Showing fallback data.");
    }
    
    default ApiResponse<CourseDto> getCourseByCodeFallback(String courseCode, Exception ex) {
        CourseDto fallbackCourse = CourseDto.builder()
                .courseCode(courseCode)
                .courseName("Service Unavailable")
                .credits(0)
                .status(com.studentmanagement.enrollment.dto.Course.CourseStatus.INACTIVE)
                .build();
        
        return ApiResponse.success(fallbackCourse, "Course service is currently unavailable. Showing fallback data.");
    }
}
