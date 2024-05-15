package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TeacherCourse
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonManagedReference(value = "teacher-course")
    private Teacher teacher;
    @ManyToOne
    @JsonIgnore
    private Course course;
    @ManyToOne
    @JsonIgnore
    private Semester semester;
    @OneToOne
    private Classroom classroom;
    @OneToOne
    private ElectiveClassroom electiveClassroom;

    private String section;


    public TeacherCourse() {
    }

    public TeacherCourse(long id, Teacher teacher, Course course, Semester semester, String section) {
        this.id = id;
        this.teacher = teacher;
        this.course = course;
        this.semester = semester;
        this.section = section;
    }
}
