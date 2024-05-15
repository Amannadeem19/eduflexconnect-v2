package com.fyp.CourseRegistration.Requests;

import com.fyp.CourseRegistration.Models.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesRequest
{
    List<OfferedCourseRequest> OfferedCourses = new ArrayList<>();
    List<Enrollment> enrollments = new ArrayList<>();
}
