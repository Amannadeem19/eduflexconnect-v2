package com.fyp.eduflexconnect.DtoMapper;


import com.fyp.eduflexconnect.DTOs.*;
import com.fyp.eduflexconnect.Models.Comment;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Util.StudentUtil;
import com.fyp.eduflexconnect.Util.TeacherUtil;

import java.util.ArrayList;
import java.util.List;

public class CommentDtoMapper {

    public static CommentDtoStudent ToCommentDtoStudent(Comment comment, Student student){

        AnnouncementDtoForTeacher announcementDto;
        AnnouncementDto announcementDtoStudent;



        StudentDto studentDto = StudentDtoMapper.toStudentDto(comment.getStudent());

        CommentDtoStudent  commentDtoStudent = new CommentDtoStudent();
        if(comment.getAnnouncement().getStudent() != null){
             announcementDtoStudent = AnnouncementDtoMapper
                    .toAnnouncementDto(comment.getAnnouncement(), comment.getAnnouncement().getStudent());
             commentDtoStudent.setStudentAnnouncement(announcementDtoStudent);
            //comment jis announcement kia ha uskeuser ko find karke check kar rha ke comment usi ne kia ha yaa nh
            commentDtoStudent.setReqUser(StudentUtil.isReqUser(comment.getAnnouncement().getStudent(),student));
        }
        if(comment.getAnnouncement().getTeacher() != null){
            announcementDto = AnnouncementDtoMapperTeacher
                    .toAnnouncementDtoForTeacher(comment.getAnnouncement(), comment.getAnnouncement().getTeacher());
            commentDtoStudent.setTeacherAnnouncement(announcementDto);
        }
        commentDtoStudent.setId(comment.getId());
        commentDtoStudent.setMsg(comment.getMsg());
        commentDtoStudent.setCreatedAt(comment.getCreatedAt());
        commentDtoStudent.setCommentedBy(studentDto);

        return commentDtoStudent;
    }
    public static CommentDtoTeacher ToCommentDtoTeacher(Comment comment, Teacher teacher){

        AnnouncementDtoForTeacher announcementDto;
        AnnouncementDto announcementDtoStudent;

        TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(comment.getTeacher());
        CommentDtoTeacher  commentDtoTeacher = new CommentDtoTeacher();
        // agar announcement teacher ki ha
        if(comment.getAnnouncement().getTeacher() != null){
            announcementDto = AnnouncementDtoMapperTeacher
                .toAnnouncementDtoForTeacher(comment.getAnnouncement(), comment.getAnnouncement().getTeacher());
            commentDtoTeacher.setTeacherAnnouncement(announcementDto);

            //comment jis announcement kia ha uskeuser ko find karke check kar rha ke comment usi ne kia ha yaa nh
            commentDtoTeacher.setReqUser(TeacherUtil.isReqUser(comment.getAnnouncement().getTeacher(), teacher));
        }
        if(comment.getAnnouncement().getStudent() != null){
            announcementDtoStudent = AnnouncementDtoMapper
                    .toAnnouncementDto(comment.getAnnouncement(), comment.getAnnouncement().getStudent());
            commentDtoTeacher.setStudAnnouncement(announcementDtoStudent);
        }
        commentDtoTeacher.setId(comment.getId());
        commentDtoTeacher.setMsg(comment.getMsg());
        commentDtoTeacher.setCreatedAt(comment.getCreatedAt());
        commentDtoTeacher.setCommentedBy(teacherDto);
        return commentDtoTeacher;
    }

    public static List<CommentsDto> toCommentDtos(List<Comment> comments){
        List<CommentsDto> commentsDtos = new ArrayList<>();
        for (Comment c : comments){
            CommentsDto commentsDto = new CommentsDto();
            StudentDto studentDto;
            TeacherDto teacherDto;
            if(c.getTeacher() != null){
                teacherDto  = TeacherDtoMapper.toTeacherDto(c.getTeacher());
                commentsDto.setCommentedByT(teacherDto);
            }if(c.getStudent() != null){
                studentDto = StudentDtoMapper.toStudentDto(c.getStudent());
                commentsDto.setCommentedBy(studentDto);
            }
            commentsDto.setId(c.getId());
            commentsDto.setMsg(c.getMsg());
            commentsDto.setCreatedAt(c.getCreatedAt());

            commentsDtos.add(commentsDto);
        }
        return commentsDtos;
    }
}
