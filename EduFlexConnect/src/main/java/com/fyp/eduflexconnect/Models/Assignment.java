package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title, description;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private Teacher createdBy;
    @JsonIgnore
    @ManyToMany(mappedBy = "assignments")
    private List<Student> students = new ArrayList<>();
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<FilesEntity> filesEntityList = new ArrayList<>();
    @JoinColumn(name = "course_id")
    @ManyToOne
    private Course course;
    @JsonIgnore
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();

}
