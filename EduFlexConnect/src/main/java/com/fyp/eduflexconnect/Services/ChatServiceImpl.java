package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.ChatException;
import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private CourseService courseService;

    @Override
    public Chat createGroup(GroupChatRequest reqData, Teacher teacher, String class_id) throws UserException, CustomException {
        Classroom classroom = classroomService.findClassroomById(class_id);
        Course isTeacherEnrolled = courseService.GetCourse(classroom.getCourse().getCourse_id());
        // yeh condition wese ani nh chaiye
        if(isTeacherEnrolled == null){
            throw new CustomException("No course enrolled inside the classroom");
        }
        if(!isTeacherEnrolled.getTeacher().contains(teacher)){
            throw new CustomException("You are not a teacher of this course , cannot create course");
        }
        if(chatRepository.existsByClassroom(classroom)){
            throw new CustomException("Chat group for this class has already created");
        }
        Chat groupChat = new Chat();
        groupChat.setGroup(true);
        groupChat.setChat_name(reqData.getGroup_name());
        groupChat.setChat_image(reqData.getGroup_image());
        groupChat.setCreatedBy(teacher);
        for(String studentId : reqData.getStudentIds()){
            Student std = studentService.findStudentByUsername(studentId);
            groupChat.getStudents().add(std);
        }

        groupChat.setClassroom(classroom);
        return chatRepository.save(groupChat);
    }

    @Override
    public Chat addUserToGroup(String student_id, Long chat_id, Teacher reqUser) throws UserException, ChatException{
        Chat chat = chatRepository.findById(chat_id).orElseThrow(() -> new ChatException("Chat not found with this id"));

        Student student = studentService.findStudentByUsername(student_id);
        if(chat.getCreatedBy().equals(reqUser)  && !chat.getStudents().contains(student)){
            chat.getStudents().add(student);
            return chatRepository.save(chat);
        }else if(chat.getStudents().contains(student)){
            throw new UserException("Student already added in a group");
        }
        else {
            throw new UserException("Only admin/teacher can add/remove students");
        }
    }
    @Override
    public Chat removeUserFromGroup(String student_id, Long chat_id, Teacher reqUser) throws UserException, ChatException{
        Chat chat = chatRepository.findById(chat_id).orElseThrow(() -> new ChatException("Chat not found with this id"));
        Student student = studentService.findStudentByUsername(student_id);
        if(chat.getCreatedBy().equals(reqUser)){
            chat.getStudents().remove(student);
            return chatRepository.save(chat);
        }
        else{
            throw new UserException("Only admin/teacher can add/remove students");
        }

    }

    @Override
    public Optional <Chat> findChatById(String classroom_id, Student student) throws ChatException {
        Classroom classroom = classroomService.findClassroomById(classroom_id);
        if(chatRepository.existsByClassroom(classroom)){
          Optional  <Chat>  chat = chatRepository.findByClassroom(classroom);
          if(chat.get().getStudents().contains(student)){
              return chat;
          }
          throw new ChatException("Student is not part of this group");

        }
       throw new ChatException("Classroom has not found group chat");
    }

    public Optional <Chat> findChatById(String classroom_id, Teacher teacher) throws ChatException {
        Classroom classroom = classroomService.findClassroomById(classroom_id);
        if(chatRepository.existsByClassroom(classroom)){
            Optional  <Chat>  chat = chatRepository.findByClassroom(classroom);
            if(chat.get().getCreatedBy().getUsername().contains(teacher.getUsername())){
                return chat;

            }
            throw new ChatException("Teacher is not part of this group");
        }
        throw new ChatException("Classroom has not found group chat");
    }
}
