package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Section {
    @Id
    private String id;
    private int enrollYear;
    private String department;
    private int semester;
    @OneToMany
    @JsonBackReference
    private List<Classroom> classrooms;



    public Section() {
    }

    public Section(String id, int enrollYear, String department, int semester, List<Classroom> classrooms) {
        this.id = id;
        this.enrollYear = enrollYear;
        this.department = department;
        this.semester = semester;
        this.classrooms = classrooms;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEnrollYear(int enrollYear) {
        this.enrollYear = enrollYear;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }
}
