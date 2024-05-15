package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Lecture;
import com.fyp.eduflexconnect.Models.LectureFeedback;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.FeedbackRepository;
import com.fyp.eduflexconnect.Repositories.LectureRepository;
import com.fyp.eduflexconnect.Repositories.StudentRepository;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.Response.FeedbackResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.FeedbackService;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Services.TeacherService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    @PostMapping("/{classroomId}")
    public Lecture createLectureFeedbackHandler(@RequestBody Lecture lecture,
                                                @PathVariable("classroomId") String classroomId,
                                                @RequestHeader("Authorization")String jwt) throws UserException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
//        System.out.println("courseid" +  courseId);
        if(roles.contains("Role_TEACHER")){
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            return feedbackService.createLectureFeedback(lecture, classroomId, teacher);

        }else{
            throw new UserException("You have not accessed for this api");
        }

    }
    @PostMapping("/add/{classroomId}/lecture/{lectureId}")
    public ApiResponse addFeedbackHandler(@RequestBody LectureFeedback feedback,
                                                @PathVariable("lectureId")Long lectureId,
                                                @PathVariable("classroomId") String classroomId,
                                              @RequestHeader("Authorization") String jwt) throws UserException{
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if(roles.contains("Role_STUDENT")){
        Student student = studentService.findStudentProfileByJwt(token);

            LectureFeedback lectureFeedback =  feedbackService.addFeedback(feedback,lectureId,classroomId, student);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Feedback Added Successfully");
            apiResponse.setStatus(true);
            return apiResponse;
        }else{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("You are authenticated to add feedback");
            apiResponse.setStatus(false);
            return apiResponse;
        }

    }

    @GetMapping("/rating/{classroomId}")
    public  FeedbackResponse findAvgRatingEachTeacherCousre(@PathVariable("classroomId")  String classroomId,
                                                            @RequestHeader("Authorization")String jwt) throws UserException{

        String token  = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if (roles.contains("Role_TEACHER")){
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            FeedbackResponse feedbackResponse =  feedbackService.findAverageRating(teacher, classroomId);
            return feedbackResponse;
        }
        throw new UserException("You can not access the teacher rating");
    }

    @GetMapping("/feedback/{classroomId}")
    public  List<LectureFeedback> getAllFeedbacks(@PathVariable("classroomId")  String classroomId,
                                                            @RequestHeader("Authorization")String jwt) throws UserException{

        String token  = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if (roles.contains("Role_TEACHER")){
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            List<LectureFeedback> feedbacks = feedbackService.findAllCourseFeedBack(classroomId, teacher);
            return feedbacks;
        }
        throw new UserException("You can not access the teacher rating");
    }

    @GetMapping("/lectures/{classroomId}")
    public List<Lecture> getAllLecturesHandler(@PathVariable("classroomId")  String classroomId,
                                                            @RequestHeader("Authorization")String jwt) throws UserException{
        String token  = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if (roles.contains("Role_TEACHER") || roles.contains("Role_STUDENT")){
           return feedbackService.findAllCourseLectures(classroomId);
        }
        throw new UserException("You can not access the teacher rating");
    }


}
