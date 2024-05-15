package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.ChatException;
import com.fyp.eduflexconnect.Exceptions.MessageException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Message;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Request.MessageRequest;

import java.util.List;

public interface MessageService {

    public Message sendMessage(MessageRequest req, Student student) throws UserException, ChatException;
    public List<Message> getChatMessages(String classroomId, Student student) throws ChatException;
    public Message findMessageById(Long messageId) throws MessageException;
    public void deleteMessageById(Long messageId, Student student) throws UserException,MessageException;
    public Message updateMessageById(MessageRequest request,Long messageId, Student student ) throws UserException,MessageException;

}
