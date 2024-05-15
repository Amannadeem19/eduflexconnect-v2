package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Enrollment;
import com.fyp.eduflexconnect.Models.Semester;
import com.fyp.eduflexconnect.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Integer>
{
    @Query("Select case when COUNT(e) > 0 then true else false end from Enrollment e where e.course.course_code = :courseCode and e.student.id = :student_id and e.semester.semester_id = :semester_id")
    boolean isRegister(@Param("courseCode") String courseCode, @Param("student_id") String student_id, @Param("semester_id") String semester_id);

    List<Enrollment> findByStudentAndSemester(Student student, Semester semester);

    List<Enrollment> findByCourseAndSemesterAndCourseSection(Course course, Semester semester, String course_section);

    Enrollment findByStudentAndSemesterAndCourse(Student student, Semester semester,Course course );
    Enrollment findByStudent(Student student);
}
