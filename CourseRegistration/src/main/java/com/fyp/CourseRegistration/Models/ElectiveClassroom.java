package com.fyp.CourseRegistration.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectiveClassroom
{
    @Id
    @GeneratedValue(generator = "elective_classroom_id_generator",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "elective_classroom_id_generator",sequenceName = "elective_classroom_id_generator",allocationSize = 1)
    private int elective_classroom_id;
    @OneToOne
    @JoinColumn(name = "elective_section_id")
    @JsonIgnore
    private ElectiveSection electiveSection;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    @JsonIgnore
    private Semester semester;
    @ManyToMany(mappedBy = "electiveClassrooms")
    @JsonBackReference
    private List<Student> students = new ArrayList<>();
}
