package com.fyp.CourseRegistration.Services;

import com.fyp.CourseRegistration.Models.Course;
import com.fyp.CourseRegistration.Models.ElectiveClassroom;
import com.fyp.CourseRegistration.Models.ElectiveSection;
import com.fyp.CourseRegistration.Models.Semester;
import com.fyp.CourseRegistration.Repositories.ElectiveClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectiveClassroomService
{
    @Autowired
    private ElectiveClassroomRepository electiveClassroomRepository;

    public ElectiveClassroom findElectiveClassroom(Course course, Semester semester, ElectiveSection electiveSection)
    {
        return electiveClassroomRepository.findByCourseAndSemesterAndElectiveSection(course,semester,electiveSection);
    }

    public ElectiveClassroom saveElectiveClassroom(ElectiveClassroom electiveClassroom)
    {
        return electiveClassroomRepository.save(electiveClassroom);
    }
}
