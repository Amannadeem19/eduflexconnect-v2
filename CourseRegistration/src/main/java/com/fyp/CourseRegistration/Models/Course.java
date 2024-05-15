package com.fyp.CourseRegistration.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course
{

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
    @JsonIgnore
    private List<OfferedCourse> offeredCourses;
    @JsonIgnore
    @ManyToMany
    private List<Teacher> teacher = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

}
