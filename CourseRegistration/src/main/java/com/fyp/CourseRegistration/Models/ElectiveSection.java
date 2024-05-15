package com.fyp.CourseRegistration.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
