package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Enrollment
{
    @Id
    @GeneratedValue(generator = "enrollment_id_generator",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "enrollment_id_generator",sequenceName = "enrollment_id_generator",allocationSize = 1)
    private int enrollment_id;

    private String courseSection;

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;


    private int semester_number;

    public Enrollment() {
    }

    public Enrollment(int enrollment_id, String course_section, Student student, Course course, Semester semester, int semester_number) {
        this.enrollment_id = enrollment_id;
        this.courseSection = course_section;
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.semester_number = semester_number;
    }

    public Enrollment(int enrollment_id, Student student, Course course, Semester semester, int semester_number) {
        this.enrollment_id = enrollment_id;
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.semester_number = semester_number;
    }
}
