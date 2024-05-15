package com.fyp.eduflexconnect.DTOs;

import com.fyp.eduflexconnect.Models.Classroom;
import com.fyp.eduflexconnect.Models.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatGroupDto {
    private  Long id;
    private String chat_name,chat_image;
    private Classroom classroom;
    private List<StudentDto> listStudents;
    private TeacherDto admin;

    // List of messages

//    private List<Message>messages;

}
