package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content, image, video, type;
    private LocalDateTime createdAt;
    @ManyToOne
    @JsonIgnore
    private Student student;
    @ManyToOne
    @JsonIgnore
    private Teacher teacher;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

}
