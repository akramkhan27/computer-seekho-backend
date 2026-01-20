package com.computerseekho.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "album_master")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Integer albumId;

    @Column(name = "album_name", nullable = false, length = 100)
    private String albumName;

    @Column(name = "album_description", columnDefinition = "TEXT")
    private String albumDescription;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "album_is_active")
    private Boolean albumIsActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ---------- Audit ----------

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}