package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class OfferedElectiveCourse
{
    String course_code;
    int semester_number;
    List<String> department_names;
    String semester_id;

    public OfferedElectiveCourse()
    {

    }

    public OfferedElectiveCourse(String course_code, int semester_number, List<String> department_names, String semester_id) {
        this.course_code = course_code;
        this.semester_number = semester_number;
        this.department_names = department_names;
        this.semester_id = semester_id;
    }
}
