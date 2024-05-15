package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Department {
    @Id
    @SequenceGenerator(name = "department_id_generator", sequenceName = "department_id_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_generator")
    private int id;
    private String department_name;
    private String department_code; // Code Means CS/SE/AI
    @OneToMany(mappedBy = "department")
    @JsonBackReference
    private List<OfferedCourse> offeredCourses;


    public Department()
    {

    }

    public Department(int id, String department_name, String department_code, List<OfferedCourse> offeredCourses) {
        this.id = id;
        this.department_name = department_name;
        this.department_code = department_code;
        this.offeredCourses = offeredCourses;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepartment_id(String department_name) {
        this.department_name = department_name;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public void setOfferedCourses(List<OfferedCourse> offeredCourses) {
        this.offeredCourses = offeredCourses;
    }
}
