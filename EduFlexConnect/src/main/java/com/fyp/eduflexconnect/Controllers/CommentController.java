package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.DTOs.CommentDtoStudent;
import com.fyp.eduflexconnect.DTOs.CommentDtoTeacher;
import com.fyp.eduflexconnect.DTOs.CommentsDto;
import com.fyp.eduflexconnect.DtoMapper.CommentDtoMapper;
import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.CommentException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Announcement;
import com.fyp.eduflexconnect.Models.Comment;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.AnnouncementService;
import com.fyp.eduflexconnect.Services.CommentService;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Services.TeacherService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private CommentService commentService;
//    ResponseEntity<AnnouncementResponse<?>>

    @PostMapping("/{announce_id}")
    public ResponseEntity<AnnouncementResponse<?>> createComment(@RequestBody Comment comment,
                                                 @PathVariable Long announce_id,
                                                 @RequestHeader("Authorization") String jwt)
    throws UserException, AnnouncementException {

        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean checkRole = jwtTokenProvider.getAuthorities(token);
        if (checkRole) {
                System.out.println("you are student");
                Student student = studentService.findStudentProfileByJwt(token);
                Announcement announcement = announcementService.findById(announce_id);
                Comment comment1 = commentService.createCommentByStudent(comment, announcement, student);
                CommentDtoStudent commentDtoStudent = CommentDtoMapper.ToCommentDtoStudent(comment1, student);
//            naming sahe nh ha tw confuse nh hona
                AnnouncementResponse<CommentDtoStudent> announcementResponse = new AnnouncementResponse<>();
                announcementResponse.setType("student");
                announcementResponse.setUpdated(false);
                announcementResponse.setAnnouncement(commentDtoStudent);

                return new ResponseEntity<>(announcementResponse, HttpStatus.CREATED);
            } else {
                System.out.println("you are teacher");
                Teacher teacher = teacherService.findTeacherProfileByJwt(token);
                Announcement announcement = announcementService.findById(announce_id);
                Comment comment1 = commentService.createCommentByTeacher(comment, announcement, teacher);
                CommentDtoTeacher commentDtoTeacher = CommentDtoMapper.ToCommentDtoTeacher(comment1, teacher);
                AnnouncementResponse<CommentDtoTeacher> announcementResponse = new AnnouncementResponse<>();
                announcementResponse.setType("teacher");
                announcementResponse.setUpdated(false);
                announcementResponse.setAnnouncement(commentDtoTeacher);
                return new ResponseEntity<>(announcementResponse, HttpStatus.CREATED);
            }


    }

    @PutMapping("/{announce_id}/{comment_id}")
    public ResponseEntity<AnnouncementResponse<?>> updateComment(@RequestBody Comment comment,
                                                                 @PathVariable Long announce_id,
                                                                 @PathVariable Long comment_id,
                                                                 @RequestHeader("Authorization") String jwt)
            throws UserException, AnnouncementException, CommentException {

        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean checkRole = jwtTokenProvider.getAuthorities(token);
        if (checkRole) {
            System.out.println("you are student");
            Student student = studentService.findStudentProfileByJwt(token);
            Announcement announcement = announcementService.findById(announce_id);
            Comment comment1 = commentService.updateCommentByStudent(comment, announcement, comment_id , student);

            CommentDtoStudent commentDtoStudent = CommentDtoMapper.ToCommentDtoStudent(comment1, student);
//            naming sahe nh ha tw confuse nh hona
            AnnouncementResponse<CommentDtoStudent> announcementResponse = new AnnouncementResponse<>();
            announcementResponse.setType("student");
            announcementResponse.setUpdated(true);
            announcementResponse.setAnnouncement(commentDtoStudent);

            return new ResponseEntity<>(announcementResponse, HttpStatus.CREATED);
        } else {
            System.out.println("you are teacher");
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            Announcement announcement = announcementService.findById(announce_id);
            Comment comment1 = commentService.updateCommentByTeacher(comment, announcement,comment_id ,teacher);
            CommentDtoTeacher commentDtoTeacher = CommentDtoMapper.ToCommentDtoTeacher(comment1, teacher);
            AnnouncementResponse<CommentDtoTeacher> announcementResponse = new AnnouncementResponse<>();
            announcementResponse.setType("teacher");
            announcementResponse.setUpdated(true);
            announcementResponse.setAnnouncement(commentDtoTeacher);
            return new ResponseEntity<>(announcementResponse, HttpStatus.CREATED);
        }


    }
    @GetMapping("/{announce_id}")
    public ResponseEntity<List<CommentsDto>> getAllAnnouncementComments(@PathVariable Long announce_id,
                                                                        @RequestHeader("Authorization") String jwt
                                                                    ) throws UserException, AnnouncementException{
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        Announcement announcement = announcementService.findById(announce_id);
        List<Comment> comments = commentService.findAllAnnouncementComment(announcement.getId());
        List<CommentsDto> commentsDtos = CommentDtoMapper.toCommentDtos(comments);
        return new ResponseEntity<>(commentsDtos, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{announce_id}/{comment_id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long announce_id,
                                                     @PathVariable Long comment_id,
                                                     @RequestHeader("Authorization")String jwt)
            throws UserException, AnnouncementException, CommentException{
        ApiResponse apiResponse;
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean checkRole = jwtTokenProvider.getAuthorities(token);
        if (checkRole){
            System.out.println("you are student");
            Student student = studentService.findStudentProfileByJwt(token);
            Announcement announcement = announcementService.findById(announce_id);
            commentService.DeleteCommentByStudent(comment_id, student);
            apiResponse = new ApiResponse("Comment Deleted Successfully", true);

        }else{
            System.out.println("you are student");
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            Announcement announcement = announcementService.findById(announce_id);
            commentService.DeleteCommentByTeacher(comment_id, teacher);
            apiResponse = new ApiResponse("Comment Deleted Successfully", true);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

}
