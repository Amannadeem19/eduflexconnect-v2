package com.fyp.CourseRegistration.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferedCourse
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offered_courses_id;

    private int semesterNumber;
    @ManyToOne
    @JoinColumn(name = "course_id")

    private Course course;

    @ManyToOne
    @JoinColumn(name = "department_id")

    private Department department;


    @ManyToOne
    @JoinColumn(name = "semester_id")

    private Semester semester;
}
