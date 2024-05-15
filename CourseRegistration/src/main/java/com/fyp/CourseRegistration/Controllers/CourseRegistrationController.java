package com.fyp.CourseRegistration.Controllers;


import com.fyp.CourseRegistration.DTO.CourseRegistrationDTO;
import com.fyp.CourseRegistration.Models.Enrollment;
import com.fyp.CourseRegistration.Requests.CoursesRequest;
import com.fyp.CourseRegistration.Requests.OfferedCourseRequest;
import com.fyp.CourseRegistration.SecurityConfig.JwtTokenProvider;
import com.fyp.CourseRegistration.Services.Clients.EduFlexClient;
import com.fyp.CourseRegistration.Services.StudentCourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courseregistration")
public class CourseRegistrationController
{
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private StudentCourseRegistrationService studentCourseRegistrationService;
    @Autowired
    private EduFlexClient eduFlexClient;

    @GetMapping("/courseregistration/page")
    public CoursesRequest courseregistrationpage(@RequestHeader("Authorization") String authorizationHeader)
    {

        // Bearer ko remove kia he token ke start se.
        String token = authorizationHeader.substring(7);
        String student_id = jwtTokenProvider.getUsernameFromToken(token);
        String semester_id = eduFlexClient.currentSemester();


        List<OfferedCourseRequest> offeredCourseRequests = studentCourseRegistrationService.courseRegistrationPage(semester_id,student_id);
        List<Enrollment> enrollments = studentCourseRegistrationService.getStudentRegisteredCourses(semester_id,student_id);

        return new CoursesRequest(offeredCourseRequests,enrollments);
    }

    @PostMapping("/courseregistration")
    public void courseregistration(@RequestHeader("Authorization") String authorizationHeader, @RequestBody List<CourseRegistrationDTO> courses)
    {
        // Bearer ko remove kia he token ke start se.
        String token = authorizationHeader.substring(7);
        String student_id = jwtTokenProvider.getUsernameFromToken(token);
        String semester_id = eduFlexClient.currentSemester();

        studentCourseRegistrationService.courseRegistration(courses,student_id,semester_id);
    }
}
