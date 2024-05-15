package com.fyp.eduflexconnect.DTOs;

import lombok.Getter;

import java.util.List;

@Getter
public class OfferedCourseDTO
{
    private List<String> course_code;
    private String department_name;
    private String semester_id;
    private int semester_number;

    public OfferedCourseDTO()
    {
    }

    public OfferedCourseDTO(List<String> course_code, String department_name, String semester_id, int semester_number) {
        this.course_code = course_code;
        this.department_name = department_name;
        this.semester_id = semester_id;
        this.semester_number = semester_number;
    }

    public void setCourse_code(List<String> course_code) {
        this.course_code = course_code;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public void setSemester_id(String semester_id) {
        this.semester_id = semester_id;
    }

    public void setSemester_number(int semester_number) {
        this.semester_number = semester_number;
    }
}
