package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Response.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    public Lecture createLectureFeedback(Lecture lecture, String classroomId, Teacher teacher) throws UserException;
    public LectureFeedback addFeedback(LectureFeedback feedback, Long lectureId,String classroomId, Student student) throws CustomException;
    public FeedbackResponse findAverageRating(Teacher teacher, String classroomId) throws CustomException;
    public List<Lecture> findAllCourseLectures(String classroomId) throws CustomException;

    public List<LectureFeedback> findAllCourseFeedBack(String classroomId, Teacher teacher) throws CustomException;

}
