package com.fyp.CourseRegistration.Repositories;

import com.fyp.CourseRegistration.Models.Course;
import com.fyp.CourseRegistration.Models.ElectiveSection;
import com.fyp.CourseRegistration.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectiveSectionRepository extends JpaRepository<ElectiveSection, Integer>
{
    ElectiveSection findByName(String name);
    List<ElectiveSection> findByCourseAndSemester(Course course, Semester semester);


}
