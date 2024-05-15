package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.OfferedCourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OfferedCourseService
{
    @Autowired
    private SemesterService semester_service;
    @Autowired
    private DepartmentService department_service;
    @Autowired
    private CourseService course_service;
    @Autowired
    private OfferedCourseRepository offered_course_repo;
    @Autowired
    private SectionService section_service;
    @Autowired
    private ClassroomService class_service;



    public OfferedCourseService()
    {

    }

    public List<OfferedCourse> listOfferedCourses(String section_id,Semester semester)
    {
        Section section = section_service.GetSection(section_id);
        int semester_number = section.getSemester();
        Department department = department_service.department_repo.findByDepartment_name(section.getDepartment());
        List<OfferedCourse> offered_courses = offered_course_repo.findByDepartmentAndSemesterNumberAndSemester(department,semester_number,semester);

        return offered_courses;

    }

    public List<OfferedCourse> findBySemesterandDepartmentandSemesterId(String department,int number,Semester semester)
    {
        return offered_course_repo.findByDepartmentAndSemesterNumberAndSemester(department_service.department_repo.findByDepartment_name(department),number,semester);
    }
    @Transactional
    public void storeOfferedCourses(List<String> courseCodes, String departmentName,String semesterID, int semesterNumber)
    {
        Set<String> unique_course_codes = new HashSet<>();
        for(String code: courseCodes)
        {
            if(!unique_course_codes.add(code))
            {
                throw new CustomException("Course Code: "+code+" is duplicate");
            }
        }
        Department department = department_service.department_repo.findByDepartment_name(departmentName);
        if (department == null)
        {
            throw new CustomException("Invalid Department");

        }

        if(semesterNumber<1 || semesterNumber >8)
        {
            throw new CustomException("Invalid Semester Number");
        }

        // Find Semester by number
        Semester semester = semester_service.semester_repo.findById(semesterID).orElse(null);
        if (semester == null || semester.getStatus().equals("Passed"))
        {
            throw  new CustomException("Invalid or already passed semester");

        }


        List<Course> courses = new ArrayList<>();
        for (String courseCode : courseCodes)
        {
            Course course = course_service.course_repo.findByCode(courseCode);
            if (course == null)
            {
                throw new CustomException("Invalid Course Code: " + courseCode);
            }
            else
            {
                OfferedCourse offered_course = offered_course_repo.findDuplicateOfferedCourse(course,department,semester,semesterNumber);
                if(offered_course != null)
                {
                    throw new CustomException("Course Code: "+courseCode+" is already offered");
                }
                else
                {
                    courses.add(course);
                }
            }

        }
        for (Course course : courses)
        {
            OfferedCourse offeredCourse = new OfferedCourse();
            offeredCourse.setCourse(course);
            offeredCourse.setDepartment(department);
            offeredCourse.setSemester(semester);
            offeredCourse.setSemesterNumber(semesterNumber);
            offered_course_repo.save(offeredCourse);
            System.out.println("Course Code: "+course.getCourse_code()+", Name: "+course.getCourse_name()+" is offered to Department: "+department.getDepartment_name()+" for semester number: "+ semesterNumber+" in "+semester.getSemester_id()+" semester");

            List<Section> sections = section_service.section_repo.findBySemesterAndDepartment(semesterNumber,department.getDepartment_name());
            for(Section section : sections)
            {
                System.out.println(
                        class_service.GenerateClassroom(
                                semester.getSemester_id(),
                                section.getId(),
                                course.getCourse_id())
                );
            }
        }
    }

    @Transactional
    public void storeOfferedElectiveCourses(String course_code,int semester_number,List<String> department_names,String semester_id)
    {
        Course course = course_service.course_repo.findByCode(course_code);
        Semester semester = semester_service.semester_repo.findById(semester_id).orElse(null);
        List<Department> departments = new ArrayList<>();

        if(semester == null || semester.getStatus().equals("Passed"))
        {
            throw  new CustomException("Invalid or already passed semester");

        }

        for(String  d: department_names )
        {
            departments.add(department_service.department_repo.findByDepartment_name(d));
        }

        for(Department depart: departments)
        {
            OfferedCourse offeredCourse = new OfferedCourse();
            offeredCourse.setCourse(course);
            offeredCourse.setDepartment(depart);
            offeredCourse.setSemester(semester);
            offeredCourse.setSemesterNumber(semester_number);
            offered_course_repo.save(offeredCourse);
            System.out.println("Course Code: "+course.getCourse_code()+", Name: "+course.getCourse_name()+" is offered to Department: "+depart.getDepartment_name()+" for semester number: "+ semester_number+" in "+semester.getSemester_id()+" semester");

        }



    }

}
