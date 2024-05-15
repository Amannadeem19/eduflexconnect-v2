package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.ChatException;
import com.fyp.eduflexconnect.Exceptions.MessageException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Chat;
import com.fyp.eduflexconnect.Models.Message;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.ChatRepository;
import com.fyp.eduflexconnect.Repositories.MessageRepository;
import com.fyp.eduflexconnect.Request.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl  implements MessageService{

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;


    @Override
    public Message sendMessage(MessageRequest req, Student student) throws UserException, ChatException {
        Optional<Chat> chatOptional = chatService.findChatById(req.getClassroom_Id(), student);
        Message message = new Message();
        if(!chatOptional.get().getStudents().contains(student)){
            throw new ChatException("You are not part of this chat group");
        }
        if (chatOptional.isPresent()){
            Chat chat = chatOptional.get();
            message.setChat(chat);
            message.setStudent(student);
            message.setContent(req.getContent());
            message.setTimestamp(LocalDateTime.now());
            return messageRepository.save(message);

        }else{
            throw  new ChatException("No chat found for this classroom");
        }

//        return null;
    }
    public Message sendMessage(MessageRequest req, Teacher teacher) throws UserException, ChatException {
        ChatServiceImpl chatServiceImpl = (ChatServiceImpl) chatService;

        Optional<Chat> chatOptional = chatServiceImpl.findChatById(req.getClassroom_Id(), teacher);
        if(!chatOptional.get().getCreatedBy().getUsername().equals(teacher.getUsername())){
            throw new ChatException("You are not part of this chat group");
        }
        Message message = new Message();
        if (chatOptional.isPresent()){
            Chat chat = chatOptional.get();
            message.setChat(chat);
            message.setTeacher(teacher);
            message.setContent(req.getContent());
            message.setTimestamp(LocalDateTime.now());
            return messageRepository.save(message);

        }else{
            throw  new ChatException("No chat found for this classroom");
        }
    }

    @Override
    public List<Message> getChatMessages(String classroomId, Student student) throws ChatException {
        Optional<Chat> chatOptional = chatService.findChatById(classroomId, student);

        if(!chatOptional.get().getStudents().contains(student)){
            throw new ChatException("Student is not part of this chat group" + chatOptional.get().getId());
        }
        if (chatOptional.isPresent()){
            Chat chat = chatOptional.get();
            List<Message> messages = messageRepository.findByChatId(chat.getId());
            return messages;
        }else{
            throw new ChatException("No chat with this classroom");
        }
    }

    public List<Message> getChatMessages(String classroomId, Teacher teacher) throws ChatException {
        ChatServiceImpl chatServiceImpl = (ChatServiceImpl)chatService;
        Optional<Chat> chatOptional = chatServiceImpl.findChatById(classroomId, teacher);

        if(!chatOptional.get().getCreatedBy().getUsername().contains(teacher.getUsername())){
            throw new ChatException("Teacher is not part of this chat group" + chatOptional.get().getId());
        }
        if (chatOptional.isPresent()){
            Chat chat = chatOptional.get();
            List<Message> messages = messageRepository.findByChatId(chat.getId());
            return messages;
        }else{
            throw new ChatException("No chat with this classroom");
        }
    }

    @Override
    public Message findMessageById(Long messageId) throws MessageException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(()-> new MessageException("Message not found"));
        return message;
    }

    @Override
    public void deleteMessageById(Long messageId, Student student ) throws UserException,MessageException {
                Message message = findMessageById(messageId);
                if(! message.getStudent().getId().equals(student.getId())){
                    throw new UserException("You can not delete this message");
                }
                messageRepository.deleteById(message.getId());
    }
    public void deleteMessageById(Long messageId, Teacher teacher) throws UserException, MessageException {
        Message message = findMessageById(messageId);
        if(! message.getTeacher().getUsername().equals(teacher.getUsername())){
            throw new UserException("You can not delete this message");
        }
        messageRepository.deleteById(message.getId());

    }

    @Override
    public Message updateMessageById(MessageRequest request,Long messageId, Student student) throws UserException,MessageException {
        Message message = findMessageById(messageId);
        if(!message.getStudent().getId().equals(student.getId())){
            throw new UserException("You cannot update this message" + student.getId());
        }
        if(request.getContent() != null){
            message.setContent(request.getContent());
        }
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }
    public Message updateMessageById(MessageRequest request, Long messageId, Teacher teacher) throws  UserException,MessageException {
        Message message = findMessageById(messageId);
        if(!message.getTeacher().getUsername().equals(teacher.getUsername())){
            throw new UserException("You cannot update this message" + teacher.getUsername());
        }
        if(request.getContent() != null){
            message.setContent(request.getContent());
        }
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }
}
