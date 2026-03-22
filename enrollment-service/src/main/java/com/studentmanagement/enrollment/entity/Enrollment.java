package com.studentmanagement.enrollment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @NotNull(message = "Course ID is required")
    @Column(name = "course_id", nullable = false)
    private Long courseId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private EnrollmentStatus status = EnrollmentStatus.PENDING;
    
    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;
    
    @PrePersist
    protected void onCreate() {
        if (enrollmentDate == null) {
            enrollmentDate = LocalDateTime.now();
        }
    }
    
    public enum EnrollmentStatus {
        ENROLLED, COMPLETED, DROPPED, PENDING
    }
}
