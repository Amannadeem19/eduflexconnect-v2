package com.fyp.CourseRegistration.Repositories;

import com.fyp.CourseRegistration.Models.Course;
import com.fyp.CourseRegistration.Models.ElectiveClassroom;
import com.fyp.CourseRegistration.Models.ElectiveSection;
import com.fyp.CourseRegistration.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectiveClassroomRepository extends JpaRepository<ElectiveClassroom,Integer>
{
    ElectiveClassroom findByCourseAndSemesterAndElectiveSection(Course course, Semester semester, ElectiveSection electiveSection);

}

