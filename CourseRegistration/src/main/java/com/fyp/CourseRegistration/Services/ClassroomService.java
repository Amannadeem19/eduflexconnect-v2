package com.fyp.CourseRegistration.Services;

import com.fyp.CourseRegistration.Models.Classroom;
import com.fyp.CourseRegistration.Models.Course;
import com.fyp.CourseRegistration.Models.Section;
import com.fyp.CourseRegistration.Models.Semester;
import com.fyp.CourseRegistration.Repositories.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService
{
    @Autowired
    private ClassroomRepository classroom_repo;
    public Classroom findClassroom(Course course, Semester semester, Section section )
    {
        return classroom_repo.findByCourseAndSemesterAndSection(course, semester,section);
    }

    public Classroom saveClassroom(Classroom classroom)
    {
        return classroom_repo.save(classroom);
    }
}
