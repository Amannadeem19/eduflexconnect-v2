package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Marks {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long U_id;
    private float mid1;
    private float project;
    private float mid2;
    private float finalExam;
    private Float totmid1;
    private Float totmid2;
    private Float totFinal;
    private Float totProject;
    private Float totQuiz;
    private Float totAssignment;


    @ElementCollection
    @MapKeyColumn(name = "quiz_number")
    @Column(name = "quiz_mark")
    private Map<Integer,Float> quiz = new HashMap<>();


    @ElementCollection
    @MapKeyColumn(name = "assignment_number")
    @Column(name = "assignment_mark")
    private Map<Integer,Float> assignment = new HashMap<>();


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id")
    @JsonManagedReference
    private Student student;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonManagedReference
    private Course course;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonManagedReference
    private Teacher teacher;

}