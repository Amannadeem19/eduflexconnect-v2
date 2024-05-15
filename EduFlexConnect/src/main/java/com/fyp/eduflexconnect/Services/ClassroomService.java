package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.ClassroomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService
{
    @Autowired
    private ClassroomRepository class_repo;
    @Autowired
    private SemesterService semester_service;
    @Autowired
    private SectionService section_service;
    @Autowired
    private CourseService course_service;

    public void saveClassroom(Classroom classroom)
    {
        class_repo.save(classroom);
    }
    public Classroom findClassroomById(String class_id)
    {

        return class_repo.findById(class_id).orElse(null);
    }
    @Transactional
    public String GenerateClassroom(String semester_id, String section_id, int course_id)
    {
        Classroom check_exist = class_repo.findById((course_id+semester_id+section_id)).orElse(null);
        Semester semester = semester_service.GetSemester(semester_id);
        Section section = section_service.GetSection(section_id);
        Course course = course_service.GetCourse(course_id);
        if(check_exist != null)
        {
            return "Classroom with ID: "+course_id+semester_id+section_id+" already exist or ID's provided for classroom creation";
        }
        else if(semester == null || section == null || course == null)
        {
            return "Wrong inputs for class creation, IDs: "+semester+section+course;
        }
        else
        {


            Classroom newclassroom = new Classroom();
            newclassroom.setCourse(course);
            newclassroom.setSection(section);
            newclassroom.setSemester(semester);
            class_repo.save(newclassroom);

            return "Classroom with ID: "+ course_id+semester_id+section_id+" created. Course Name: "+course.getCourse_name()+", Semester: "+semester.getSemester_id()+", Section: "+section.getId();

        }
    }

    public Classroom findClassroom(Course course, Semester semester, Section section )
    {
        return class_repo.findByCourseAndSemesterAndSection(course, semester,section);
    }

    public List<Student> getStudentOfClassroom(String classroom_id)
    {
        Classroom classroom = class_repo.findById(classroom_id).orElse(null);
        assert classroom != null;
        return classroom.getStudents();
    }


}
