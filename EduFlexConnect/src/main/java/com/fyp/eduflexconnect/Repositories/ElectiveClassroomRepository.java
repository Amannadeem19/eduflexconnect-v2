package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.ElectiveClassroom;
import com.fyp.eduflexconnect.Models.ElectiveSection;
import com.fyp.eduflexconnect.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectiveClassroomRepository extends JpaRepository<ElectiveClassroom,Integer>
{
    ElectiveClassroom findByCourseAndSemesterAndElectiveSection(Course course, Semester semester, ElectiveSection electiveSection);

}

