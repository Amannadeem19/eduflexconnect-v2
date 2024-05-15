package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String msg;
    private LocalDateTime createdAt;
    @ManyToOne
    Announcement announcement;
    @ManyToOne
    Teacher teacher;
    @ManyToOne
    Student student;


}
