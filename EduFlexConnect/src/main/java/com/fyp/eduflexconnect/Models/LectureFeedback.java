package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class LectureFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedback_id;
    private String feedback;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "lect_id")

    private Lecture lecture;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

}
