package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course")
public class CourseController
{
    @Autowired
    CourseService course_service;

    @PostMapping("/addcourse")
    public Course addcourse(@RequestBody Course course)
    {
        return course_service.AddCourse(course);
    }

    @GetMapping("/listallcourses")
    public List<Course> listallcourses()
    {
        return course_service.ListAllCourses();
    }

    @PostMapping("/assigncourse/{teacher_ID}/{course_ID}")
    public ResponseEntity<String> assigncourse(@PathVariable String teacher_ID , @PathVariable int course_ID){

        try
        {

            course_service.assignTeacherToCourses(teacher_ID , course_ID);
            return new ResponseEntity<>("Course added successfully", HttpStatus.CREATED);
        } catch (Exception e)
        {
            return new ResponseEntity<>("Error adding Course: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getcourse/{courseCode}")
    public Course GetCourse(@PathVariable  String courseCode)
    {
        return course_service.GetCourse(courseCode);
    }

}
