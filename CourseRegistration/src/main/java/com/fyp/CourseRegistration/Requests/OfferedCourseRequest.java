package com.fyp.CourseRegistration.Requests;

import com.fyp.CourseRegistration.Models.Course;
import com.fyp.CourseRegistration.Models.ElectiveSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferedCourseRequest
{
    private Course course;
    private List<ElectiveSection> availableSections = new ArrayList<>();
}
