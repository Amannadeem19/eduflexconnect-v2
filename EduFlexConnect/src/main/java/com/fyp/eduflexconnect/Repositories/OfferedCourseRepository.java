package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Department;
import com.fyp.eduflexconnect.Models.OfferedCourse;
import com.fyp.eduflexconnect.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferedCourseRepository extends JpaRepository<OfferedCourse,Long>
{
    @Query("Select o from OfferedCourse o where o.course = :courseID AND o.department = :departmentID AND o.semester = :semesterID AND o.semesterNumber = :semesterNumber")
    OfferedCourse findDuplicateOfferedCourse(@Param("courseID") Course courseID, @Param("departmentID") Department departmentID, @Param("semesterID") Semester semesterID, @Param("semesterNumber") int semesterNumber);

    List<OfferedCourse> findByDepartmentAndSemesterNumberAndSemester(Department department,int number,Semester semester);




}
