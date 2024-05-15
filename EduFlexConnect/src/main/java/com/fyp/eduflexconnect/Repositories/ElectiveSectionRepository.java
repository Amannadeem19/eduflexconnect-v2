package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.ElectiveSection;
import com.fyp.eduflexconnect.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ElectiveSectionRepository extends JpaRepository<ElectiveSection, Integer>
{
    ElectiveSection findByName(String name);
    List<ElectiveSection> findByCourseAndSemester(Course course, Semester semester);


}
