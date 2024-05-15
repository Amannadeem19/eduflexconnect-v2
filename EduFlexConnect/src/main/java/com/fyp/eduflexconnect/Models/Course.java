package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(generator = "course_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "course_id_generator", sequenceName = "course_id_generator", allocationSize = 1)
    private int course_id;
    @Column(unique = true)
    private String course_code;
    private String course_name;
    private String description;
    private String domain;
    private int credit_hours;
    private String type;

    @OneToMany(mappedBy = "course")
    @JsonBackReference
    private List<OfferedCourse> offeredCourses;
    @JsonIgnore
    @ManyToMany
    private List<Teacher> teacher = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();
    @OneToMany
    @JsonBackReference
    private List<Classroom> classrooms;
    @OneToMany
    @JsonBackReference
    private List<ElectiveClassroom> electiveClassrooms;


    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lecture> lectures = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<LectureFeedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Assignment> assignments = new ArrayList<>();
    public Course()
    {

    }

    public Course(int course_id, String course_code, String course_name, String description, String domain, int credit_hours, String type, List<OfferedCourse> offeredCourses, List<Teacher> teacher, List<Enrollment> enrollments, List<Classroom> classrooms, List<ElectiveClassroom> electiveClassrooms) {
        this.course_id = course_id;
        this.course_code = course_code;
        this.course_name = course_name;
        this.description = description;
        this.domain = domain;
        this.credit_hours = credit_hours;
        this.type = type;
        this.offeredCourses = offeredCourses;
        this.teacher = teacher;
        this.enrollments = enrollments;
        this.classrooms = classrooms;
        this.electiveClassrooms = electiveClassrooms;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setCredit_hours(int credit_hours) {
        this.credit_hours = credit_hours;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOfferedCourses(List<OfferedCourse> offeredCourses) {
        this.offeredCourses = offeredCourses;
    }

    public void setTeacher(List<Teacher> teacher)
    {
        this.teacher = teacher;
    }
}
