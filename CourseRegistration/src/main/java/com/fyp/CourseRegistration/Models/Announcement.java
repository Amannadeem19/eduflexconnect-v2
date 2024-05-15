package com.fyp.CourseRegistration.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content, image, video, type;
    private LocalDateTime createdAt;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
