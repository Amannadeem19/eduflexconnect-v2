package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Lecture;
import com.fyp.eduflexconnect.Models.LectureFeedback;
import com.fyp.eduflexconnect.Models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<LectureFeedback, Long> {

    List<LectureFeedback> findByTeacherAndCourse(Teacher teacher, Course course);
    List<LectureFeedback> findByLecture(Lecture lecture);


}
