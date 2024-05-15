package com.fyp.CourseRegistration.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment
{

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
