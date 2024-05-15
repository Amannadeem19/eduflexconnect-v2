package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Exceptions.ChatException;
import com.fyp.eduflexconnect.Exceptions.MessageException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Message;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.StudentRepository;
import com.fyp.eduflexconnect.Request.MessageRequest;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.MessageService;
import com.fyp.eduflexconnect.Services.MessageServiceImpl;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Services.TeacherService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/chat/message")
@RestController
public class MessageController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/{classroomId}")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody MessageRequest messageRequest,
                                                      @RequestHeader("Authorization")String jwt,
                                                      @PathVariable("classroomId")String classId) throws
            UserException, ChatException {

        messageRequest.setClassroom_Id(classId);
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean isStudent = jwtTokenProvider.getAuthorities(token);
        List<String> roles = jwtTokenProvider.findRole(token);
        if (isStudent){
            // you are student
            Student student = studentService.findStudentProfileByJwt(token);
            Message message = messageService.sendMessage(messageRequest, student);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        else if(roles.contains("Role_TEACHER")){
            Teacher teacher =  teacherService.findTeacherProfileByJwt(token);
            MessageServiceImpl messageServiceImpl = (MessageServiceImpl) messageService;
            Message message = messageServiceImpl.sendMessage(messageRequest, teacher);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        throw new UserException("You are not either teacher and student");
    }
    @GetMapping("/{classroomId}")
    public ResponseEntity<List<Message>> getChatMessagesHandler(@RequestHeader("Authorization")String jwt,
                                                      @PathVariable("classroomId")String classId) throws
            UserException, ChatException {

        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean isStudent = jwtTokenProvider.getAuthorities(token);
        List<String> roles = jwtTokenProvider.findRole(token);
        if (isStudent){
            // you are student
            Student student = studentService.findStudentProfileByJwt(token);
            List<Message> chatMessages = messageService.getChatMessages(classId,student);
            return new ResponseEntity<>(chatMessages, HttpStatus.OK);
        }
        else if(roles.contains("Role_TEACHER")){
            Teacher teacher =  teacherService.findTeacherProfileByJwt(token);
            MessageServiceImpl messageServiceImpl = (MessageServiceImpl) messageService;
            List<Message> chatMessages = messageServiceImpl.getChatMessages(classId, teacher);
            return new ResponseEntity<>(chatMessages, HttpStatus.CREATED);
        }
        throw new UserException("You are not either teacher and student");
    }
    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> getChatMessagesHandler(@RequestHeader("Authorization")String jwt,
                                                              @PathVariable("messageId")Long messageId) throws
            UserException, ChatException, MessageException {

        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean isStudent = jwtTokenProvider.getAuthorities(token);
        List<String> roles = jwtTokenProvider.findRole(token);
        if (isStudent){
            // you are student
            Student student = studentService.findStudentProfileByJwt(token);
             messageService.deleteMessageById(messageId, student);
             ApiResponse apiResponse = new ApiResponse();
             apiResponse.setMessage("Message deleted Successfully");
             apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        else if(roles.contains("Role_TEACHER")){
            Teacher teacher =  teacherService.findTeacherProfileByJwt(token);
            MessageServiceImpl messageServiceImpl = (MessageServiceImpl) messageService;
            messageServiceImpl.deleteMessageById(messageId, teacher);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Message deleted Successfully");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        throw new UserException("You are not either teacher and student");
    }
    @PutMapping("/update/{messageId}")
    public ResponseEntity<Message> updateMessageHandler(@RequestBody MessageRequest messageRequest,
                                                      @RequestHeader("Authorization")String jwt,
                                                      @PathVariable("messageId")Long messageId) throws
            UserException, ChatException, MessageException {


        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean isStudent = jwtTokenProvider.getAuthorities(token);
        List<String> roles = jwtTokenProvider.findRole(token);
        if (isStudent){
            // you are student
            Student student = studentService.findStudentProfileByJwt(token);
            Message message = messageService.updateMessageById(messageRequest, messageId, student);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else if(roles.contains("Role_TEACHER")){
            Teacher teacher =  teacherService.findTeacherProfileByJwt(token);
            MessageServiceImpl messageServiceImpl = (MessageServiceImpl) messageService;
            Message message = messageServiceImpl.updateMessageById(messageRequest, messageId, teacher);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        throw new UserException("You are not either teacher and student");
    }
}
