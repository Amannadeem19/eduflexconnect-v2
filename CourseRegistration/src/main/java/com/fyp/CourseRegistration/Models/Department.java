package com.fyp.CourseRegistration.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Department
{
    @Id
    @SequenceGenerator(name = "department_id_generator", sequenceName = "department_id_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_generator")
    private int id;
    private String department_name;
    private String department_code; // Code Means CS/SE/AI
    @OneToMany(mappedBy = "department")

    private List<OfferedCourse> offeredCourses;
}
