package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Classroom;
import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Section;
import com.fyp.eduflexconnect.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository <Classroom, String>
{
    Classroom findByCourseAndSemesterAndSection(Course course, Semester semester, Section section);
}
