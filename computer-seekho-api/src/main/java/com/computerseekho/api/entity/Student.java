package com.computerseekho.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Student_Master")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "student_name", nullable = false, length = 100)
    private String studentName;

    @Column(name = "student_address", length = 200)
    private String studentAddress;

    @Column(name = "student_gender", length = 10)
    private String studentGender;

    @Column(name = "photo_url", length = 255)
    private String photoUrl;

    @Column(name = "student_dob")
    private LocalDate studentDob;

    @Column(name = "student_qualification", length = 50)
    private String studentQualification;

    @Column(name = "student_mobile", nullable = false)
    private Long studentMobile;

    // FOREIGN KEY (batch_master)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    @Column(name = "student_password", length = 255)
    private String studentPassword;

    @Column(name = "student_username", unique = true, length = 100)
    private String studentUsername;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // AUTO TIMESTAMP HANDLING
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
