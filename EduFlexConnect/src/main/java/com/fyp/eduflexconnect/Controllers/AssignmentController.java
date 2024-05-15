package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.DTOs.AssignmentDto;
import com.fyp.eduflexconnect.DTOs.SubmissionDto;
import com.fyp.eduflexconnect.DtoMapper.AssignmentDtoMapper;
import com.fyp.eduflexconnect.DtoMapper.SubmissionDtoMapper;
import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Assignment;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Submission;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Request.AssignmentRequest;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.AssignmentService;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Services.TeacherService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {
    @Autowired
   private StudentService studentService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/upload/{courseId}")
    public ResponseEntity<AssignmentDto> createAssignmentHandler(@RequestParam("document") MultipartFile[] files,
                                                                @RequestParam("title") String title,
                                                                @RequestParam("description")String description,
                                                                @RequestParam("deadline")String deadline, @RequestHeader("Authorization") String jwt, @PathVariable int courseId)
        throws UserException, CustomException, IOException {

        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
        // Create DateTimeFormatter with the defined pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime deadline1 = LocalDateTime.parse(deadline, formatter);
       String token = COMMONFuncs.separateBearerFromToken(jwt);
       List<String> roles = jwtTokenProvider.findRole(token);
       if(roles.contains("Role_TEACHER")){
           AssignmentRequest assignmentRequest = new AssignmentRequest();
           assignmentRequest.setDeadline(deadline1);
           assignmentRequest.setTitle(title);
           assignmentRequest.setDescription(description);
           Teacher teacher = teacherService.findTeacherProfileByJwt(token);
           Assignment assignment =  assignmentService.createAssignment(assignmentRequest, files, courseId, teacher);
            AssignmentDto assignmentDto= AssignmentDtoMapper.toAssignmentDto(assignment);
            return new ResponseEntity<>(assignmentDto, HttpStatus.CREATED);
       }else{
           throw new CustomException("You are not a teacher");
       }

    }

    @PostMapping("/course/{courseId}/assign/{assignId}")
    public ResponseEntity<SubmissionDto> uploadSubmissionHandler(@PathVariable("courseId")int courseId,
                                                                 @PathVariable("assignId")Long assignId,
                                                                 @RequestHeader("Authorization")String jwt,
                                                                 @RequestParam("document") MultipartFile[] files)
            throws CustomException, UserException, IOException{

        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if(roles.contains("Role_STUDENT")){
            Student student = studentService.findStudentProfileByJwt(token);
            Submission submission = assignmentService.uploadAssignment(assignId, courseId, student, files);
            SubmissionDto submissionDto = SubmissionDtoMapper.toSubmitDto(submission);
            return new ResponseEntity<>(submissionDto, HttpStatus.OK);
        }else{
            throw new CustomException("You are not a student");
        }
    }

    @GetMapping("/submissions/{assignId}")
    public ResponseEntity<List<SubmissionDto>> getAssignSubmissionsHandler(@RequestHeader("Authorization")String jwt,
                                                        @PathVariable("assignId") Long assignId)throws CustomException, UserException, IOException{
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if(roles.contains("Role_TEACHER")){
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
           List<Submission> submissions = assignmentService.getAssignmentsubmissions(teacher, assignId);
            List<SubmissionDto> submissionDtos = SubmissionDtoMapper.toSubmissionDtos(submissions);
            return new ResponseEntity<>(submissionDtos, HttpStatus.OK);
        }else{
            throw new CustomException("You are not a teacher to access this api");
        }
    }
}
