package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Society {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
    private String societyName;
    private String title, content, image, video, type;
    private LocalDateTime createdAt;
    @ManyToOne
    private SuperAdmin admin;
}
