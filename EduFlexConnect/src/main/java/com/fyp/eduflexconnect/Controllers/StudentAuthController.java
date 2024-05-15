package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.DTOs.CourseRegistrationDTO;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Attendance;
import com.fyp.eduflexconnect.Models.Enrollment;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Repositories.StudentRepository;
import com.fyp.eduflexconnect.Request.ClassroomRequest;
import com.fyp.eduflexconnect.Response.AuthResponse;
import com.fyp.eduflexconnect.SecurityConfig.ApplicationUserRoles;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/student")
public class StudentAuthController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Autowired
    private StudentService student_service;
    @Autowired
    private SemesterService semester_service;
    @Autowired
    private StudentCourseRegistrationService student_course_reg_service;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping ("/generatestudentdata/{arn}/{section_id}")
    public ResponseEntity<AuthResponse> createStudent(@PathVariable int arn, @PathVariable String section_id) throws UserException
    {

        Student student = student_service.GenerateStudentData(arn,section_id);
        Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.STUDENT.getGrantedAuthorities();
        List<SimpleGrantedAuthority> studentAuths = new ArrayList<>(authSet);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                    student.getId(),
                student.getLogin_password(),
                studentAuths
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token, true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
    @PostMapping("/signin")

    public ResponseEntity<AuthResponse> Signin(@RequestBody Student student)
    {
        System.out.println(student);
        String username = student.getId();
        String password = student.getLogin_password();
        Authentication authentication = authenticate(username, password);
        System.out.println("authentication" +  authentication);
        String token =  jwtTokenProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token, true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!password.equals(userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(
            userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    @GetMapping("/courseregistration/page")
    public String courseregistrationpage(@RequestHeader("Authorization") String authorizationHeader)
    {

        // Bearer ko remove kia he token ke start se.
        String token = authorizationHeader.substring(7);
        String student_id = jwtTokenProvider.getUsernameFromToken(token);
        String semester_id = semester_service.currentSemester();


        return student_course_reg_service.courseRegistrationPage(semester_id,student_id) + student_id;
    }

    @PostMapping("/courseregistration")
    public void courseregistration(@RequestHeader("Authorization") String authorizationHeader, @RequestBody List<CourseRegistrationDTO> courses)
    {
        // Bearer ko remove kia he token ke start se.
        String token = authorizationHeader.substring(7);
        String student_id = jwtTokenProvider.getUsernameFromToken(token);
        String semester_id = semester_service.currentSemester();

        student_course_reg_service.courseRegistration(courses,student_id,semester_id);
    }

    @GetMapping ("/studentclassrooms")
    public ClassroomRequest studentclassrooms(@RequestHeader("Authorization") String authorizationHeader) throws UserException {
        // Bearer ko remove kia he token ke start se.
        String token = authorizationHeader.substring(7);
        String student_id = jwtTokenProvider.getUsernameFromToken(token);
        String semester_id = semester_service.currentSemester();

        List<Enrollment> enrollments = enrollmentService.getCurrentEnrollments(student_id,semester_id);
        return enrollmentService.getCurrentClassrooms(enrollments);
    }

    @GetMapping("/getattendance/{courseCode}/{semesterId}")
    public List<Attendance> getAttendance(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String courseCode,@PathVariable String semesterId)
    {
        String token = authorizationHeader.substring(7);
        String student_id = jwtTokenProvider.getUsernameFromToken(token);

        return attendanceService.getStudentAttendanceCourse(student_id,courseCode,semesterId);

    }





}
