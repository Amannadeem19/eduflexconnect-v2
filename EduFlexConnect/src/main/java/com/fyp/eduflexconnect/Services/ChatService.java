package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.ChatException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Chat;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;

import java.util.Optional;


public interface ChatService {

    public Chat createGroup(GroupChatRequest reqData, Teacher teacher, String class_id) throws UserException;
    public Chat addUserToGroup(String student_id, Long chat_id, Teacher reqUser) throws UserException, ChatException;
    public Chat removeUserFromGroup(String student_id, Long chat_id, Teacher reqUser) throws UserException, ChatException;
    public Optional<Chat> findChatById(String chat_id, Student student) throws ChatException;

}

