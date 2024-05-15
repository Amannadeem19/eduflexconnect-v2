package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Enrollment;
import com.fyp.eduflexconnect.Services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/enrollment")
public class EnrollmentController
{
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/checkregistration/{course_code}/{student_id}/{semester_id}")
    public boolean isRegister(@PathVariable String course_code,@PathVariable String student_id,@PathVariable String semester_id)
    {
        return enrollmentService.isRegister(course_code,student_id,semester_id);

    }

    @GetMapping("/getcurrentenrollments/{studentId}/{semesterId}")
    public List<Enrollment> getStudentCurrentEnrollments(@PathVariable String studentId,@PathVariable String semesterId) throws UserException {
        return enrollmentService.getCurrentEnrollments(studentId,semesterId);
    }
}
