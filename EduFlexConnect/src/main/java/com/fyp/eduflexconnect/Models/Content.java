package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<FilesEntity> filesEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
