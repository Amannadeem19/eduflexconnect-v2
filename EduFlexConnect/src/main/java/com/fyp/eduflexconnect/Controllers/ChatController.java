package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.DTOs.ChatGroupDto;
import com.fyp.eduflexconnect.DtoMapper.ChatGroupDtoMapper;
import com.fyp.eduflexconnect.Exceptions.ChatException;
import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Chat;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.*;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/{c_id}")
    //assuming student already registered courses
    public ResponseEntity<ChatGroupDto> createChatGroupHandler(@RequestBody GroupChatRequest reqData,
                                                               @PathVariable("c_id") String class_id,
                                                               @RequestHeader("Authorization") String jwt)
            throws UserException, CustomException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean isStudent = jwtTokenProvider.getAuthorities(token); // return true - if it is not a teacher
        if(!isStudent){
            System.out.println("chat me teacher"+ isStudent);
            System.out.println(reqData);
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            Chat groupCreated = chatService.createGroup(reqData, teacher, class_id);
            ChatGroupDto chatGroupDto = ChatGroupDtoMapper.toChatGroupDto(groupCreated);
            return new ResponseEntity<>(chatGroupDto, HttpStatus.CREATED);
        }
        throw new CustomException("You are Student/Admin, you can't create chat group");
    }

    @GetMapping("/{c_id}")
    //assuming student already registered courses
    public ResponseEntity<ChatGroupDto> getGroup(
            @PathVariable("c_id") String classroom_id,
            @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException , CustomException{
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean isStudent = jwtTokenProvider.getAuthorities(token); // return true - if it is not a teacher
        if (isStudent){

            Student student =  studentService.findStudentProfileByJwt(token);
            Optional <Chat> groupUpdated = chatService.findChatById(classroom_id, student);
            if (groupUpdated == null){
                throw new ChatException("No group found");
            }
            ChatGroupDto chatGroupDto = ChatGroupDtoMapper.toChatGroupDto(groupUpdated);
            return new ResponseEntity<>(chatGroupDto, HttpStatus.OK);
        }
       else{
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            ChatServiceImpl chatServiceImpl = (ChatServiceImpl) chatService;
            Optional <Chat> groupUpdated = chatServiceImpl.findChatById(classroom_id, teacher);
            if (groupUpdated == null){
                throw new ChatException("No group found");
            }
            ChatGroupDto chatGroupDto = ChatGroupDtoMapper.toChatGroupDto(groupUpdated);
            return new ResponseEntity<>(chatGroupDto, HttpStatus.OK);
        }

    }

    @PutMapping("/{c_id}/group/add/{user_id}")
    //assuming student already registered courses
    public ResponseEntity<ChatGroupDto> addUserToGroupHandler(
                                                               @PathVariable("c_id") Long chat_id,
                                                               @PathVariable("user_id") String student_id,
                                                               @RequestHeader("Authorization") String jwt)
            throws UserException, CustomException, ChatException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean isStudent = jwtTokenProvider.getAuthorities(token); // return true - if it is not a teacher
        if(!isStudent){

            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            Chat groupUpdated = chatService.addUserToGroup(student_id, chat_id, teacher);
            ChatGroupDto chatGroupDto = ChatGroupDtoMapper.toChatGroupDto(groupUpdated);
            return new ResponseEntity<>(chatGroupDto, HttpStatus.OK);
        }
        throw new CustomException("You are Student/Admin, you can't add student in a chat group");
    }

    @PutMapping("/{c_id}/group/remove/{user_id}")
    //assuming student already registered courses
    public ResponseEntity<ChatGroupDto> removeUserToGroupHandler(
                                                              @PathVariable("c_id") Long chat_id,
                                                              @PathVariable("user_id") String student_id,
                                                              @RequestHeader("Authorization") String jwt)
            throws UserException, CustomException, ChatException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean isStudent = jwtTokenProvider.getAuthorities(token); // return true - if it is not a teacher
        if(!isStudent){

            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            Chat groupUpdated = chatService.removeUserFromGroup(student_id, chat_id, teacher);
            ChatGroupDto chatGroupDto = ChatGroupDtoMapper.toChatGroupDto(groupUpdated);
            return new ResponseEntity<>(chatGroupDto, HttpStatus.OK);
        }
        throw new CustomException("You are Student/Admin, you can't remove student from chat group");
    }






}
