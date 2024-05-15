package com.fyp.CourseRegistration.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonManagedReference
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    @JsonIgnore
    private Semester semester;


    private int semester_number;
}
