package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.TeacherCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherCourseService
{
    @Autowired
    private TeacherCourseRepository teacherCourseRepository;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private ElectiveSectionService electiveSectionService;
    @Autowired
    private ElectiveClassroomService electiveClassroomService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SemesterService semesterService;


    public TeacherCourse assignCourse(String teacherId, String  courseCode, String section_id, String semesterId)
    {
        Teacher teacher = teacherService.getTeacher(teacherId);
        Course course = courseService.GetCourse(courseCode);
        Semester semester = semesterService.GetSemester(semesterId);
        if(teacherCourseRepository.existsByTeacherAndCourseAndSemesterAndSection(teacher,course,semester,section_id))
        {
            throw new CustomException("Teacher already assigned");
        }
        else
        {
            TeacherCourse newAssignment = new TeacherCourse();
            if(course.getType().equals("Core"))
            {
                Section section = sectionService.GetSection(section_id);
                Classroom existing_classroom = classroomService.findClassroom(course,semester,section);
                newAssignment.setTeacher(teacher);
                newAssignment.setCourse(course);
                newAssignment.setSection(section.getId());
                newAssignment.setSemester(semester);
                newAssignment.setClassroom(existing_classroom);
                teacherCourseRepository.save(newAssignment);
                System.out.println("Assigned successfully");
                existing_classroom.setTeacher(teacher);
                classroomService.saveClassroom(existing_classroom);
                return newAssignment;
            }
            else
            {
                ElectiveSection electiveSection = electiveSectionService.getElectiveSection(section_id);
                ElectiveClassroom existing_elective_classroom = electiveClassroomService.findElectiveClassroom(course,semester,electiveSection);
                newAssignment.setTeacher(teacher);
                newAssignment.setCourse(course);
                newAssignment.setSection(electiveSection.getName());
                newAssignment.setSemester(semester);
                newAssignment.setElectiveClassroom(existing_elective_classroom);
                teacherCourseRepository.save(newAssignment);
                System.out.println("Assigned successfully");
                existing_elective_classroom.setTeacher(teacher);
                electiveClassroomService.saveElectiveClassroom(existing_elective_classroom);
                return newAssignment;

            }
        }
    }

    public List<TeacherCourse> teachercurrentcourse(Teacher teacher, String semester)
    {
        return teacherCourseRepository.findByTeacherAndSemester(teacher,semesterService.GetSemester(semester));
    }
}
