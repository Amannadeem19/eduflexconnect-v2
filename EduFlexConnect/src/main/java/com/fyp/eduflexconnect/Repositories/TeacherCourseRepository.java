package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Semester;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Models.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long>
{
    boolean existsByTeacherAndCourseAndSemesterAndSection(Teacher teacher, Course course, Semester semester, String section);

    List<TeacherCourse> findByTeacherAndSemester(Teacher teacher, Semester semester);
}
