package com.fyp.CourseRegistration.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classroom
{

    @Id
    @GeneratedValue(generator = "classroom_id_generator")
    @GenericGenerator(name = "classroom_id_generator",strategy = "com.fyp.eduflexconnect.Generators.ClassIDGenerator")
    private String classroom_id;
    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private Section section;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    @JsonIgnore
    private Semester semester;
    @ManyToMany(mappedBy = "classrooms")
    @JsonBackReference
    private List<Student> students = new ArrayList<>();
}
