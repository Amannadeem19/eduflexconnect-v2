package com.fyp.eduflexconnect.Repositories;
import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Marks;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks,Long> {
    @Query("SELECT m.U_id FROM Marks m WHERE m.student.id = :student and m.course.course_id = :course")
    Long findMarksIdByStudentCourse(@Param("student") String student, @Param("course") int course );
    @Query("SELECT m.U_id FROM Marks m WHERE m.teacher.username = :teacher and m.course.course_id = :course")
    Long findMarksIdTeacherCourse(@Param("teacher") String teacher, @Param("course") int course );

    @Query("SELECT m.U_id FROM Marks m WHERE m.student.id = :student and m.course.course_id = :course and m.teacher.username = :teacher")
    Long findMarksId(@Param("student") String student, @Param("course") int course ,@Param("teacher") String teacher);
    @Query("SELECT m.mid1 FROM Marks m where m.student.id = :student and m.course.course_id = :course and m.teacher.username =:teacher")
    float findDataFromMid1(@Param("student") String student, @Param("course") int course , @Param("teacher")String teacher);

    @Query("SELECT m.mid2 FROM Marks m where m.student = :student and m.course = :course ")
    float findDataFromMid2(@Param("student") String student, @Param("course") int course);

    @Query("SELECT m.quiz FROM Marks m where m.student = :student and m.course = :course ")
    float findDataFromQuiz(@Param("student") String student, @Param("course") int course);

    @Query("SELECT m.assignment FROM Marks m where m.student = :student and m.course = :course ")
    float findDataFromAssignment(@Param("student") String student, @Param("course") int course);

    @Query("SELECT m.project FROM Marks m where m.student = :student and m.course = :course ")
    float findDataFromProject(@Param("student") String student, @Param("course") int course);

    @Query("SELECT m.finalExam FROM Marks m where m.student = :student and m.course = :course  ")
    float findDataFromFinalExam(@Param("student") String student, @Param("course") int course);

    @Query("SELECT  m.U_id ,m.mid1,m.student.id from Marks m where m.course.course_id=:course and m.teacher.username =:teacher")
    List<Object[]> findMid1MarksTeacher( @Param("course") int course , @Param("teacher")String teacher);
    @Query("SELECT  m.U_id ,m.mid2,m.student.id from Marks m where m.course.course_id=:course and m.teacher.username =:teacher")
    List<Object[]> findMid2MarksTeacher( @Param("course") int course , @Param("teacher")String teacher);

    @Query("SELECT  m.U_id ,m.finalExam,m.student.id from Marks m where m.course.course_id=:course and m.teacher.username =:teacher")
    List<Object[]> findFinalMarksTeacher( @Param("course") int course , @Param("teacher")String teacher);

    @Query("SELECT  m.U_id ,m.project,m.student.id from Marks m where m.course.course_id=:course and m.teacher.username =:teacher")
    List<Object[]> findProjectMarksTeacher( @Param("course") int course , @Param("teacher")String teacher);

    @Query("SELECT  m.U_id ,m.assignment,m.student.id from Marks m where m.course.course_id=:course and m.teacher.username =:teacher")
    List<Object[]> findAssignmentMarksTeacher( @Param("course") int course , @Param("teacher")String teacher);

    @Query("SELECT  m.U_id ,m.quiz,m.student.id from Marks m where m.course.course_id=:course and m.teacher.username =:teacher")
    List<Object[]> findQuizMarksTeacher( @Param("course") int course , @Param("teacher")String teacher);
    //    SELECT  m.U_id ,m.mid1,m.student.id from Marks m where m.course.course_id=:course and m.teacher.username =:teacher"
    @Query("SELECT m.U_id, m.mid1, m.student.id  from Marks m where m.course.course_id=:course and m.student.id =:student")
    List<Object[]> findMid1MarksStudent( @Param("course") int course , @Param("student")String student);

    @Query("SELECT m.student.id ,m.mid1 from Marks m where m.course.course_id=:course and m.student.id =:student")
    List<Object[]> findMid2MarksStudent( @Param("course") int course , @Param("student")String student);
    @Query("SELECT m.student.id ,m.mid2 from Marks m where m.course.course_id=:course and m.student.id =:student")
    List<Object[]> findFinalMarksStudent( @Param("course") int course , @Param("student")String student);
    @Query("SELECT m.student.id ,m.finalExam from Marks m where m.course.course_id=:course and m.student.id =:student")
    List<Object[]> findProjectMarksStudent( @Param("course") int course , @Param("student")String student);

//
//        @Query("SELECT m.student.id ,m.project from Marks m where m.course.course_id=:course and m.student.id =:student")
//    List<Map<Integer,Float>> findQuizMarksStudent(@Param("marksId") long marksId);

    @Query("SELECT  m.student.id,m.assignment from Marks m where m.course.course_id=:course and m.student.id =:student")
    List<Object[]> findAssignmentMarksStudent( @Param("course") int course , @Param("student")String student);

    @Query("SELECT m.mid1   FROM Marks m where m.U_id=:u_id")
    float findMid1OfStudent(@Param("u_id") long u_id);
    @Query("SELECT m.mid2   FROM Marks m where m.U_id=:u_id")
    float findMid2OfStudent(@Param("u_id") long u_id);
    @Query("SELECT m.assignment FROM Marks m where m.U_id=:u_id")
    List<Float> findAssignmentOfStudent(@Param("u_id") long u_id);
    @Query("SELECT m.finalExam   FROM Marks m where m.U_id=:u_id")
    float findFinalExamOfStudent(@Param("u_id") long u_id);
    @Query("SELECT m.project   FROM Marks m where m.U_id=:u_id")
    float findProjectOfStudent(@Param("u_id") long u_id);

    List<Marks> findByTeacherAndCourse(Teacher teacher, Course course);
    List<Marks> findByStudentAndCourse(Student student, Course course);

}