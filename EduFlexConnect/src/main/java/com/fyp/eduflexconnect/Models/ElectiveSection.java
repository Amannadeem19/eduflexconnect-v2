package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ElectiveSection
{
    @Id
    @GeneratedValue(generator = "elective_section_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "elective_section_id", sequenceName = "elective_section_id",allocationSize = 1)
    private int elective_section_id;

    private String name;
    private int numberOfSeats;
    private int currentEnrollments;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;


    public ElectiveSection()
    {
        currentEnrollments=0;
    }

    public ElectiveSection(int elective_section_id, String name, int numberOfSeats, int currentEnrollments, Course course, Semester semester) {
        this.elective_section_id = elective_section_id;
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.currentEnrollments = currentEnrollments;
        this.course = course;
        this.semester = semester;
    }
}
