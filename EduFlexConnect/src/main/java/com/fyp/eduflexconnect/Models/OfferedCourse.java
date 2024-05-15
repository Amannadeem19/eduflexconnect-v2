package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OfferedCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offered_courses_id;

    private int semesterNumber;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonManagedReference
    private Course course;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonManagedReference
    private Department department;


    @ManyToOne
    @JoinColumn(name = "semester_id")
    @JsonManagedReference
    private Semester semester;

    public OfferedCourse() {
    }

    public OfferedCourse(Long offered_courses_id, int semesterNumber, Course course, Department department, Semester semester) {
        this.offered_courses_id = offered_courses_id;
        this.semesterNumber = semesterNumber;
        this.course = course;
        this.department = department;
        this.semester = semester;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public void setOffered_courses_id(Long offered_courses_id) {
        this.offered_courses_id = offered_courses_id;
    }

    public void setSemesterNumber(int offered_in_semester_number) {
        this.semesterNumber = offered_in_semester_number;
    }
}