package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.DTOs.ElectiveSectionDTO;
import com.fyp.eduflexconnect.DTOs.OfferedCourseDTO;
import com.fyp.eduflexconnect.DTOs.OfferedElectiveCourse;
import com.fyp.eduflexconnect.Models.ElectiveSection;
import com.fyp.eduflexconnect.Models.TeacherCourse;
import com.fyp.eduflexconnect.Services.ElectiveSectionService;
import com.fyp.eduflexconnect.Services.EnrollmentService;
import com.fyp.eduflexconnect.Services.OfferedCourseService;
import com.fyp.eduflexconnect.Services.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController
{
    @Autowired
    private ClassroomController class_controller;
    @Autowired
    OfferedCourseService offered_course_service;
    @Autowired
    EnrollmentService enrollmentService;
    @Autowired
    ElectiveSectionService elective_section_service;
    @Autowired
    private TeacherCourseService teacherCourseService;


    @GetMapping("/generateclassroom/{semester_id}/{section_id}/{course_id}")
    public String generateclassroom(@PathVariable String semester_id,@PathVariable String section_id,@PathVariable int course_id)
    {
        return class_controller.generateclassroom(semester_id,section_id,course_id);
    }

    @PostMapping("/offercourses")
    public void offercourses(@RequestBody OfferedCourseDTO offered_course)
    {
        offered_course_service.storeOfferedCourses(
                offered_course.getCourse_code(),
                offered_course.getDepartment_name(),
                offered_course.getSemester_id(),
                offered_course.getSemester_number()
        );
    }

    @PostMapping("/offerelectivecourse")
    public void offerelectivecourses(@RequestBody OfferedElectiveCourse offered_elective_course)
    {
        offered_course_service.storeOfferedElectiveCourses(
                offered_elective_course.getCourse_code(),
                offered_elective_course.getSemester_number(),
                offered_elective_course.getDepartment_names(),
                offered_elective_course.getSemester_id()
        );
    }

    @GetMapping("/enrollstudent/{department}/{semester_id}")
    public void EnrollStudentOfSemester1(@PathVariable String department,@PathVariable String semester_id)
    {
        enrollmentService.enrollStudentOfSemester1(department,semester_id);
    }

    @PostMapping("/generateelectivesection")
    public ElectiveSection GenerateElectiveSection(@RequestBody ElectiveSectionDTO electiveSection)
    {
        return elective_section_service.generateElectiveSection(electiveSection);
    }

    @GetMapping("/assigncourse/{teacherId}/{courseCode}/{section}/{semesterId}")
    public TeacherCourse AssignCourse(@PathVariable String teacherId,@PathVariable String courseCode,@PathVariable String section,@PathVariable String semesterId)
    {
        return teacherCourseService.assignCourse(teacherId,courseCode,section,semesterId);
    }


}
