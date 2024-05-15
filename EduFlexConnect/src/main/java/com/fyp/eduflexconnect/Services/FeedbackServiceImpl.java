package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.FeedbackRepository;
import com.fyp.eduflexconnect.Repositories.LectureRepository;
import com.fyp.eduflexconnect.Response.FeedbackResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    private CourseService courseService;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private StudentCourseRegistrationService registrationService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private SemesterService semesterService;

    @Override
    public Lecture createLectureFeedback(Lecture lecture, String classroomId, Teacher teacher) throws UserException {
        Classroom classroom = classroomService.findClassroomById(classroomId);

        Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
        System.out.println("course");
        System.out.println(course);
        if(!course.getTeacher().contains(teacher)){
            throw new UserException("you are not a teacher of this course");
        }
        Lecture lecture1 = new Lecture();
        lecture1.setTopic(lecture.getTopic());
        lecture1.setContent(lecture.getContent());
        lecture1.setCreatedAt(LocalDateTime.now());
        lecture1.setTeacher(teacher);
        lecture1.setCourse(course);

        return lectureRepository.save(lecture1);
    }

    @Override
    public LectureFeedback addFeedback(LectureFeedback feedback,
                                       Long lectureId,
                                       String classroomId,
                                       Student student) throws CustomException {
        Classroom classroom = classroomService.findClassroomById(classroomId);
        Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(()-> new CustomException("lecture is not found"));
        String semesterId = semesterService.currentSemester();

        List<LectureFeedback> lectureFeedbacks = feedbackRepository.findByLecture(lecture);
        for (LectureFeedback f : lectureFeedbacks){
            if(f.getStudent().equals(student)){
                throw new CustomException("You already give the feedback");
            }
        }
        // check the condition  is student registered in the course
        Enrollment enrolled = registrationService.findEnrollment(student, semesterId, course.getCourse_id());
        if(enrolled == null){
            throw new CustomException("You are not enrolled in this course");
        }
        LectureFeedback feedback1 = new LectureFeedback();
        feedback1.setFeedback(feedback.getFeedback());
        feedback1.setStudent(student);
        feedback1.setRating(feedback.getRating());
        feedback1.setCourse(course);
        feedback1.setLecture(lecture);
        feedback1.setTeacher(lecture.getTeacher());
        return feedbackRepository.save(feedback1);
    }
    @Override
    public FeedbackResponse findAverageRating(Teacher teacher, String classroomId) throws CustomException {
        Classroom classroom = classroomService.findClassroomById(classroomId);
        Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
        List<LectureFeedback> feedbacks = feedbackRepository.findByTeacherAndCourse(teacher, course);
        double avg_rating = 0.0;
        int sum = 0;
        for (LectureFeedback feedback : feedbacks){
            sum+=feedback.getRating();
        }
        avg_rating = sum/feedbacks.size();
        System.out.println("average rating is :" + avg_rating);

        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setTeacher_name(teacher.getUsername());
        feedbackResponse.setCourse_name(course.getCourse_name());
        feedbackResponse.setRating(avg_rating);

        return feedbackResponse;
    }

    @Override
    public List<Lecture> findAllCourseLectures(String classroomId) throws CustomException {
       Classroom classroom = classroomService.findClassroomById(classroomId);
       Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
        List<Lecture> lectures =lectureRepository.findByCourse(course);
        return lectures;
    }

    @Override
    public List<LectureFeedback> findAllCourseFeedBack(String classroomId, Teacher teacher) throws CustomException {
        Classroom classroom = classroomService.findClassroomById(classroomId);
        Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
        List<LectureFeedback> feedbacks = feedbackRepository.findByTeacherAndCourse(teacher, course);
        return feedbacks;
    }
}
