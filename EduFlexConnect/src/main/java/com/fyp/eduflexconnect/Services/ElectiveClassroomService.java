package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.ElectiveClassroomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectiveClassroomService
{
    @Autowired
    private CourseService course_service;
    @Autowired
    private SemesterService semester_service;
    @Autowired
    private ElectiveClassroomRepository elective_class_repo;

    public void saveElectiveClassroom(ElectiveClassroom electiveClassroom)
    {
        elective_class_repo.save(electiveClassroom);
    }

    @Transactional
    public void GenerateElectiveClassroom(int courseID,ElectiveSection electiveSection,String semesterID)
    {
        Course course = course_service.GetCourse(courseID);
        Semester semester = semester_service.GetSemester(semesterID);

        ElectiveClassroom newElectiveClassroom =  new ElectiveClassroom();
        newElectiveClassroom.setCourse(course);
        newElectiveClassroom.setSemester(semester);
        newElectiveClassroom.setElectiveSection(electiveSection);
        System.out.println("Elective Classroom for the Course: "+newElectiveClassroom.getCourse().getCourse_name()+" of Section: "+newElectiveClassroom.getElectiveSection().getName()+" for the Semester: "+newElectiveClassroom.getSemester().getSemester_id()+" is created");
        elective_class_repo.save(newElectiveClassroom);

    }

    public ElectiveClassroom findElectiveClassroom(Course course, Semester semester, ElectiveSection electiveSection)
    {
        return elective_class_repo.findByCourseAndSemesterAndElectiveSection(course,semester,electiveSection);
    }

    public List<Student> getStudentOfElectiveClassroom(int id)
    {

        ElectiveClassroom electiveClassroom = elective_class_repo.findById(id).orElse(null);
        if(electiveClassroom == null)
        {
            return null;
        }
        return electiveClassroom.getStudents();
    }

}
