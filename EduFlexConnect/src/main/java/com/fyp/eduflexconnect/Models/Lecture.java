package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long lect_id;
    String topic;
    String content;
    LocalDateTime createdAt;
    @JsonIgnore
    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<LectureFeedback> feedbackList = new ArrayList<>();
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

}
