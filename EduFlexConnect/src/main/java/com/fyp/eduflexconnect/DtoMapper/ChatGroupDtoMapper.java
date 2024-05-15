package com.fyp.eduflexconnect.DtoMapper;

import com.fyp.eduflexconnect.DTOs.ChatGroupDto;
import com.fyp.eduflexconnect.DTOs.StudentDto;
import com.fyp.eduflexconnect.DTOs.TeacherDto;
import com.fyp.eduflexconnect.Models.Chat;
import com.fyp.eduflexconnect.Models.Student;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class ChatGroupDtoMapper {

    public static ChatGroupDto toChatGroupDto(Chat chat){
        ChatGroupDto chatGroupDto = new ChatGroupDto();
        chatGroupDto.setId(chat.getId());
        chatGroupDto.setChat_image(chat.getChat_image());
        chatGroupDto.setChat_name(chat.getChat_name());
        chatGroupDto.setClassroom(chat.getClassroom());
        TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(chat.getCreatedBy());
        chatGroupDto.setAdmin(teacherDto);

        List<Student> studentList = new ArrayList<>(chat.getStudents());

        List<StudentDto> studentDtoList = StudentDtoMapper.studentDtos(studentList);
        chatGroupDto.setListStudents(studentDtoList);
        return chatGroupDto;
    }

    public static ChatGroupDto toChatGroupDto(Optional <Chat> chat){
        ChatGroupDto chatGroupDto = new ChatGroupDto();
        chatGroupDto.setId(chat.get().getId());
        chatGroupDto.setChat_image(chat.get().getChat_image());
        chatGroupDto.setChat_name(chat.get().getChat_name());
        chatGroupDto.setClassroom(chat.get().getClassroom());
        TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(chat.get().getCreatedBy());
        chatGroupDto.setAdmin(teacherDto);

        List<Student> studentList = new ArrayList<>(chat.get().getStudents());

        List<StudentDto> studentDtoList = StudentDtoMapper.studentDtos(studentList);
        chatGroupDto.setListStudents(studentDtoList);
        return chatGroupDto;
    }

}
