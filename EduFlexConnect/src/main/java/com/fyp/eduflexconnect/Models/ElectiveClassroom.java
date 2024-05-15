package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ElectiveClassroom
{
    @Id
    @GeneratedValue(generator = "elective_classroom_id_generator",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "elective_classroom_id_generator",sequenceName = "elective_classroom_id_generator",allocationSize = 1)
    private int elective_classroom_id;
    @OneToOne
    @JoinColumn(name = "elective_section_id")
    @JsonManagedReference
    private ElectiveSection electiveSection;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonManagedReference
    private Course course;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    @JsonManagedReference(value = "electiveclassroom-semester")
    private Semester semester;
    @ManyToMany(mappedBy = "electiveClassrooms")
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();
    @ManyToOne
    @JsonManagedReference
    private Teacher teacher;
}

