package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.CommentException;
import com.fyp.eduflexconnect.Models.Announcement;
import com.fyp.eduflexconnect.Models.Comment;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;

import java.util.List;

public interface CommentService {

    public Comment createCommentByStudent(Comment comment, Announcement announcement, Student student);
    public Comment createCommentByTeacher(Comment comment, Announcement announcement, Teacher teacher);

    public Comment updateCommentByStudent(Comment comment, Announcement announcement,
                                          Long comment_id, Student student) throws CommentException;
    public Comment updateCommentByTeacher(Comment comment, Announcement announcement,
                                          Long comment_id, Teacher teacher) throws CommentException;
    public List<Comment> findAllAnnouncementComment(Long announce_id);
    public void DeleteCommentByStudent(Long comment_id, Student student) throws CommentException;
    public void DeleteCommentByTeacher(Long comment_id, Teacher teacher)throws CommentException;
}
