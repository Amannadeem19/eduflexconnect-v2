package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Services.ClassroomService;
import com.fyp.eduflexconnect.Services.ElectiveClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/classroom")
public class ClassroomController
{
    @Autowired
    private ClassroomService class_service;
    @Autowired
    private ElectiveClassroomService electiveClassroomService;

    public String generateclassroom(String semester_id, String section_id, int course_id)
    {
        return class_service.GenerateClassroom(semester_id,section_id,course_id);
    }


    @GetMapping("/getstudents/{id}")
    public List<Student> getClassroomStudents(@PathVariable String id)
    {
        return class_service.getStudentOfClassroom(id);
    }
    @GetMapping("/getstudentsofelectiveclassroom/{id}")
    public List<Student> getElectiveClassroomStudents(@PathVariable int id)
    {
        return electiveClassroomService.getStudentOfElectiveClassroom(id);
    }
}
