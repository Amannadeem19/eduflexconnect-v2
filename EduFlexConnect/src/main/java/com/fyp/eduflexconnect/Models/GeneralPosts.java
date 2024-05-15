package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class GeneralPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title, content, image, video, type;
    private LocalDateTime createdAt;
    @ManyToOne
    SuperAdmin admin;

}
