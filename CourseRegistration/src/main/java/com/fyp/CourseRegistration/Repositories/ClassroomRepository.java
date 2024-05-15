package com.fyp.CourseRegistration.Repositories;

import com.fyp.CourseRegistration.Models.Classroom;
import com.fyp.CourseRegistration.Models.Course;
import com.fyp.CourseRegistration.Models.Section;
import com.fyp.CourseRegistration.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository <Classroom, String>
{
    Classroom findByCourseAndSemesterAndSection(Course course, Semester semester, Section section);
}
