package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.CommentException;
import com.fyp.eduflexconnect.Models.Announcement;
import com.fyp.eduflexconnect.Models.Comment;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;



    @Override
    public Comment createCommentByStudent(Comment comment1,
                                          Announcement announcement, Student student) {
        Comment comment = new Comment();
        comment.setMsg(comment1.getMsg());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setStudent(student);
        comment.setAnnouncement(announcement);
        return commentRepository.save(comment);

    }
    @Override
    public Comment createCommentByTeacher(Comment comment, Announcement announcement,
                                          Teacher teacher) {
        System.out.println("comment service class");
        System.out.println(comment);

        Comment comment1 = new Comment();
        comment1.setMsg(comment.getMsg());
        comment1.setCreatedAt(LocalDateTime.now());
        comment1.setTeacher(teacher);
        comment1.setAnnouncement(announcement);
        return commentRepository.save(comment1);
    }

    public Comment findCommentById(Long id) throws CommentException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new CommentException("Comment not found with this id "+ id));
        return comment;
    }
    @Override
    public Comment updateCommentByStudent(Comment comment, Announcement announcement,
                                          Long comment_id, Student student) throws CommentException {
        Comment commentUpdate = findCommentById(comment_id);
        if(commentUpdate.getStudent().getId() != student.getId()){
            throw new CommentException("You can not update this comment");
        }
        if(comment.getMsg() != null){
            commentUpdate.setMsg(comment.getMsg());
        }
        commentUpdate.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(commentUpdate);
    }

    @Override
    public Comment updateCommentByTeacher(Comment comment, Announcement announcement,
                                          Long comment_id, Teacher teacher) throws CommentException {
        Comment commentUpdate = findCommentById(comment_id);
        if(!commentUpdate.getTeacher().getUsername().equals(teacher.getUsername()))
        {
            throw new CommentException("You can not update this comment");
        }
        if(comment.getMsg() != null){
            commentUpdate.setMsg(comment.getMsg());
        }
        commentUpdate.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(commentUpdate);
    }

    @Override
    public List<Comment> findAllAnnouncementComment(Long announce_id) {
        List<Comment> allComments = commentRepository.findByAnnouncementId(announce_id);
        return allComments;
    }

    @Override
    public void DeleteCommentByStudent(Long comment_id, Student student) throws CommentException {
        Comment comment = findCommentById(comment_id);
        if( comment.getStudent() == null){
            throw new CommentException("You can not delete this comment");
        }
        if(comment.getStudent().getId() != student.getId()){
            throw new CommentException("You can not delete this comment");
        }
        commentRepository.deleteById(comment_id);
    }


    @Override
    public void DeleteCommentByTeacher(Long comment_id, Teacher teacher) throws CommentException {
        Comment comment = findCommentById(comment_id);
        if(comment.getTeacher() == null){
            throw new CommentException("You can not delete this comment");
        }
        if(!comment.getTeacher().getUsername().equals(teacher.getUsername()))
        {
            throw new CommentException("You can not delete this comment");
        }
        commentRepository.deleteById(comment_id);
    }
}

