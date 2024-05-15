package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Attendance;
import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Semester;
import com.fyp.eduflexconnect.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Long>
{
    Attendance findByCourseAndSemesterAndStudentAndDate(Course course, Semester semester, Student student, LocalDate date);

    List<Attendance> findByStudentAndCourseAndSemester(Student student, Course course, Semester semester);


}
