package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Marks;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.EnrollmentRepository;
import com.fyp.eduflexconnect.Repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Object[]> getMid1MarksTeacher(int course_id, Teacher teacher) throws
            CustomException, UserException {

        Course course = courseService.GetCourse(course_id);
        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }
        System.out.println(course.getTeacher().contains(teacher));
        if (!course.getTeacher().contains(teacher)) {

            throw new UserException("Teacher does not exist in this course");
        }
        System.out.println("in service");
        List<Object[]> resultList = marksRepository.findMid1MarksTeacher(course_id, teacher.getUsername());
        return resultList;
    }

    public List<Object[]> getMid1MarksStudent(int course_id, String student) throws
            CustomException, UserException {
        System.out.println("in service");
        Course course = courseService.GetCourse(course_id);
        String currSemester = semesterService.currentSemester();

        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }

        if (!enrollmentRepository.isRegister(course.getCourse_code(), student, currSemester)) {
            throw new UserException("Student does not exist in this course");
        }
        List<Object[]> resultList = marksRepository.findMid1MarksStudent(course.getCourse_id(), student);
        return resultList;
    }

    //mid2Teacher
    public List<Object[]> getMid2MarksTeacher(int course_id, Teacher teacher) throws
            CustomException, UserException {

        Course course = courseService.GetCourse(course_id);
        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }
        System.out.println(course.getTeacher().contains(teacher));
        if (!course.getTeacher().contains(teacher)) {

            throw new UserException("Teacher does not exist in this course");
        }
        System.out.println("in service");
        List<Object[]> resultList = marksRepository.findMid2MarksTeacher(course_id, teacher.getUsername());
        return resultList;
    }

    //mid2Student
    public List<Object[]> getMid2MarksStudent(int course_id, String student) throws
            CustomException, UserException {
        System.out.println("in service");
        Course course = courseService.GetCourse(course_id);
        String currSemester = semesterService.currentSemester();

        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }

        if (!enrollmentRepository.isRegister(course.getCourse_code(), student, currSemester)) {
            throw new UserException("Student does not exist in this course");
        }
        List<Object[]> resultList = marksRepository.findMid2MarksStudent(course.getCourse_id(), student);
        return resultList;
    }
    //finalTeacher
    public List<Object[]> getFinalMarksTeacher(int course_id, Teacher teacher) throws
            CustomException, UserException {

        Course course = courseService.GetCourse(course_id);
        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }
        System.out.println(course.getTeacher().contains(teacher));
        if (!course.getTeacher().contains(teacher)) {

            throw new UserException("Teacher does not exist in this course");
        }
        System.out.println("in service");
        List<Object[]> resultList = marksRepository.findFinalMarksTeacher(course_id, teacher.getUsername());
        return resultList;
    }

    //finalStudent
    public List<Object[]> getFinalMarksStudent(int course_id, String student) throws
            CustomException, UserException {
        System.out.println("in service");
        Course course = courseService.GetCourse(course_id);
        String currSemester = semesterService.currentSemester();

        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }

        if (!enrollmentRepository.isRegister(course.getCourse_code(), student, currSemester)) {
            throw new UserException("Student does not exist in this course");
        }
        List<Object[]> resultList = marksRepository.findFinalMarksStudent(course.getCourse_id(), student);
        return resultList;
    }

    //projectTeacher
    public List<Object[]> getProjectMarksTeacher(int course_id, Teacher teacher) throws
            CustomException, UserException {

        Course course = courseService.GetCourse(course_id);
        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }
        System.out.println(course.getTeacher().contains(teacher));
        if (!course.getTeacher().contains(teacher)) {

            throw new UserException("Teacher does not exist in this course");
        }
        System.out.println("in service");
        List<Object[]> resultList = marksRepository.findProjectMarksTeacher(course_id, teacher.getUsername());
        return resultList;
    }

    //projectStudent
    public List<Object[]> getProjectMarksStudent(int course_id, String student) throws
            CustomException, UserException {
        System.out.println("in service");
        Course course = courseService.GetCourse(course_id);
        String currSemester = semesterService.currentSemester();

        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }

        if (!enrollmentRepository.isRegister(course.getCourse_code(), student, currSemester)) {
            throw new UserException("Student does not exist in this course");
        }
        List<Object[]> resultList = marksRepository.findProjectMarksStudent(course.getCourse_id(), student);
        return resultList;
    }


    public List<Marks> getQuizMarksStudent(int course_id, String student) throws
            CustomException, UserException {
        System.out.println("in service");
        Course course = courseService.GetCourse(course_id);
        Student student1 = studentService.findStudentByUsername(student);
        String currSemester = semesterService.currentSemester();

        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }

        if (!enrollmentRepository.isRegister(course.getCourse_code(), student, currSemester)) {
            throw new UserException("Student does not exist in this course");
        }
        Long marksId = marksRepository.findMarksIdByStudentCourse(student, course.getCourse_id());
        Marks marks = marksRepository.findById(marksId).orElseThrow(() -> {
            throw new CustomException("marks not found with this id");

        });
        List<Marks> marks1 = marksRepository.findByStudentAndCourse(student1, course);
        return marks1;
    }

    public List<Marks> getQuizMarksTeacher(int course_id, Teacher teacher) throws
            CustomException, UserException {

        Course course = courseService.GetCourse(course_id);
        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }
        System.out.println(course.getTeacher().contains(teacher));
        if (!course.getTeacher().contains(teacher)) {

            throw new UserException("Teacher does not exist in this course");
        }
        List<Marks> marks = marksRepository.findByTeacherAndCourse(teacher, course);

//        Map<Integer, Float> quiz = new HashMap<>();
//        for(Map.Entry<Integer, Float> quizes : marks.getQuiz().entrySet()){
//            quiz.put(quizes.getKey(), quizes.getValue());
//        }
        return marks;
    }

    //assignment

    public List<Marks> getAssignmentMarksStudent(int course_id, String student) throws
            CustomException, UserException {
        System.out.println("in service");
        Course course = courseService.GetCourse(course_id);
        Student student1 = studentService.findStudentByUsername(student);
        String currSemester = semesterService.currentSemester();

        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }

        if (!enrollmentRepository.isRegister(course.getCourse_code(), student, currSemester)) {
            throw new UserException("Student does not exist in this course");
        }
        Long marksId = marksRepository.findMarksIdByStudentCourse(student, course.getCourse_id());
        Marks marks = marksRepository.findById(marksId).orElseThrow(() -> {
            throw new CustomException("marks not found with this id");

        });
        List<Marks> marks1 = marksRepository.findByStudentAndCourse(student1, course);
        return marks1;
    }

    public List<Marks> getAssignmentMarksTeacher(int course_id, Teacher teacher) throws
            CustomException, UserException {

        Course course = courseService.GetCourse(course_id);
        if (course == null) {
            throw new CustomException("Course does not exist with this id");
        }
        System.out.println(course.getTeacher().contains(teacher));
        if (!course.getTeacher().contains(teacher)) {

            throw new UserException("Teacher does not exist in this course");
        }
        List<Marks> marks = marksRepository.findByTeacherAndCourse(teacher, course);
        return marks;
    }




    public float getProjectMarks(String student, int course , String teacher) {
        long marksId =  marksRepository.findMarksId(student, course , teacher);
        float project = marksRepository.findProjectOfStudent(marksId);
        return project;
    }

    public Marks addMid1(String student_username, int course_id,Teacher teacher, float marks , float totMarks) throws
            CustomException,UserException {
        System.out.println(+ marks);
        Course course = courseService.GetCourse(course_id);

        if(course == null){
            throw new CustomException("Course does not exist with this id");
        }
        if(!course.getTeacher().contains(teacher)){
            throw new UserException("Teacher does not exist in this course");
        }
        String currSemester = semesterService.currentSemester();
        if(!enrollmentRepository.isRegister(course.getCourse_code(),student_username,currSemester)){
            throw new UserException("Student does not exist in this course");
        }
        Long marksID = marksRepository.findMarksId(student_username, course_id , teacher.getUsername());
        System.out.println(marksID);
        if(marksID == null)
        {

            Student student = studentService.findStudentByUsername(student_username);
//        Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
            Marks marks1 = new Marks();
            marks1.setCourse(course);
            marks1.setStudent(student);
            marks1.setTeacher(teacher);
            marks1.setMid1(marks);
            marks1.setTotmid1(totMarks);
            return marksRepository.save(marks1);

        }
        else
        {
            Student student = studentService.findStudentByUsername(student_username);
            Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
//            Marks marks1 = new Marks();
//            marks1.setCourse(course);
//            marks1.setStudent(student);
            marks1.setMid1(marks);
            marks1.setTotmid1(totMarks);
//
            return marksRepository.save(marks1);
        }

    }

    public Marks addMid2(String student_username, int course_id,Teacher teacher, float marks, float totMarks) throws
            CustomException,UserException {
        System.out.println(+ marks);
        Course course = courseService.GetCourse(course_id);
        if(course == null){
            throw new CustomException("Course does not exist with this id");
        }
        if(!course.getTeacher().contains(teacher)){
            throw new UserException("Teacher does not exist in this course");
        }
        String currSemester = semesterService.currentSemester();
        if(!enrollmentRepository.isRegister(course.getCourse_code(),student_username,currSemester)){
            throw new UserException("Student does not exist in this course");
        }
        Long marksID = marksRepository.findMarksId(student_username, course_id , teacher.getUsername());
        System.out.println(marksID);
        if(marksID == null)
        {

            Student student = studentService.findStudentByUsername(student_username);
//        Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
            Marks marks1 = new Marks();
            marks1.setCourse(course);
            marks1.setStudent(student);
            marks1.setTeacher(teacher);
            marks1.setMid2(marks);
            marks1.setTotmid2(totMarks);
            return  marksRepository.save(marks1);

        }
        else
        {
            Student student = studentService.findStudentByUsername(student_username);
            Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
//            Marks marks1 = new Marks();
//            marks1.setCourse(course);
//            marks1.setStudent(student);
            marks1.setMid2(marks);
            marks1.setTotmid2(totMarks);
//
            return marksRepository.save(marks1);
        }

    }

    public Marks addQuiz(String student_username, int course_id,Teacher teacher, float marks , int quizId, float totMarks)
            throws CustomException,UserException {
        System.out.println(+ marks);
        Course course = courseService.GetCourse(course_id);
        if(course == null){
            throw new CustomException("Course does not exist with this id");
        }
        if(!course.getTeacher().contains(teacher)){
            throw new UserException("Teacher does not exist in this course");
        }
        String currSemester = semesterService.currentSemester();
        if(!enrollmentRepository.isRegister(course.getCourse_code(),student_username,currSemester)){
            throw new UserException("Student does not exist in this course");
        }
        Long marksID = marksRepository.findMarksId(student_username, course_id , teacher.getUsername());
        System.out.println(marksID);
        if(marksID == null)
        {

            Student student = studentService.findStudentByUsername(student_username);
//        Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
            Marks marks1 = new Marks();
            marks1.setCourse(course);
            marks1.setStudent(student);
            marks1.setTeacher(teacher);
            marks1.getQuiz().put(quizId,marks);
            marks1.setTotQuiz(totMarks);
            return    marksRepository.save(marks1);
//
        }
        else
        {
            Student student = studentService.findStudentByUsername(student_username);
            Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
//            Marks marks1 = new Marks();
//            marks1.setCourse(course);
//            marks1.setStudent(student);
            marks1.getQuiz().put(quizId,marks);
            marks1.setTotQuiz(totMarks);
            return marksRepository.save(marks1);
        }

    }

    public Marks addProject(String student_username, int course_id,Teacher teacher, float marks, float totMarks) throws
            CustomException,UserException {
        System.out.println(+ marks);
        Course course = courseService.GetCourse(course_id);
        if(course == null){
            throw new CustomException("Course does not exist with this id");
        }
        if(!course.getTeacher().contains(teacher)){
            throw new UserException("Teacher does not exist in this course");
        }
        String currSemester = semesterService.currentSemester();
        if(!enrollmentRepository.isRegister(course.getCourse_code(),student_username,currSemester)){
            throw new UserException("Student does not exist in this course");
        }
        Long marksID = marksRepository.findMarksId(student_username, course_id , teacher.getUsername());
        System.out.println(marksID);
        if(marksID == null)
        {

            Student student = studentService.findStudentByUsername(student_username);
//        Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
            Marks marks1 = new Marks();
            marks1.setCourse(course);
            marks1.setStudent(student);
            marks1.setTeacher(teacher);
            marks1.setProject(marks);
            marks1.setTotProject(totMarks);
            return    marksRepository.save(marks1);
//
        }
        else
        {
            Student student = studentService.findStudentByUsername(student_username);
            Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
//            Marks marks1 = new Marks();
//            marks1.setCourse(course);
//            marks1.setStudent(student);
            marks1.setProject(marks);
            marks1.setTotProject(totMarks);
            return marksRepository.save(marks1);
        }

    }

    public Marks addFinalExam(String student_username, int course_id,Teacher teacher, float marks, float totMarks) throws
            CustomException,UserException {
        System.out.println(+ marks);
        Course course = courseService.GetCourse(course_id);
        if(course == null){
            throw new CustomException("Course does not exist with this id");
        }
        if(!course.getTeacher().contains(teacher)){
            throw new UserException("Teacher does not exist in this course");
        }
        String currSemester = semesterService.currentSemester();
        if(!enrollmentRepository.isRegister(course.getCourse_code(),student_username,currSemester)){
            throw new UserException("Student does not exist in this course");
        }
        Long marksID = marksRepository.findMarksId(student_username, course_id , teacher.getUsername());
        System.out.println(marksID);
        if(marksID == null)
        {

            Student student = studentService.findStudentByUsername(student_username);
//        Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
            Marks marks1 = new Marks();
            marks1.setCourse(course);
            marks1.setStudent(student);
            marks1.setTeacher(teacher);
            marks1.setFinalExam(marks);
            marks1.setTotFinal(totMarks);
            return    marksRepository.save(marks1);
//
        }
        else
        {
            Student student = studentService.findStudentByUsername(student_username);
            Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
//            Marks marks1 = new Marks();
//            marks1.setCourse(course);
//            marks1.setStudent(student);
            marks1.setFinalExam(marks);
            marks1.setTotFinal(totMarks);
            return marksRepository.save(marks1);
        }

    }

    public Marks addAssignment(String student_username, int course_id,Teacher teacher, float marks,int assignmentId, float totMarks) throws
            CustomException,UserException {
        System.out.println(+ marks);
        Course course = courseService.GetCourse(course_id);
        if(course == null){
            throw new CustomException("Course does not exist with this id");
        }
        if(!course.getTeacher().contains(teacher)){
            throw new UserException("Teacher does not exist in this course");
        }
        String currSemester = semesterService.currentSemester();
        if(!enrollmentRepository.isRegister(course.getCourse_code(),student_username,currSemester)){
            throw new UserException("Student does not exist in this course");
        }
        Long marksID = marksRepository.findMarksId(student_username, course_id , teacher.getUsername());
        System.out.println(marksID);
        if(marksID == null)
        {

            Student student = studentService.findStudentByUsername(student_username);
//        Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
            Marks marks1 = new Marks();
            marks1.setCourse(course);
            marks1.setStudent(student);
            marks1.setTeacher(teacher);
            marks1.getAssignment().put(assignmentId,marks);
            marks1.setTotAssignment(totMarks);
            return    marksRepository.save(marks1);
//
        }
        else
        {
            Student student = studentService.findStudentByUsername(student_username);
            Marks marks1 = marksRepository.findById(marksID).orElseThrow(() -> new UserException("Marks ID not found"));
//            Marks marks1 = new Marks();
//            marks1.setCourse(course);
//            marks1.setStudent(student);
            marks1.getAssignment().put(assignmentId,marks);
            marks1.setTotAssignment(totMarks);
            return marksRepository.save(marks1);
        }

    }
    public Marks updateMid1Marks(long marksId, float newMid1Marks) {
        System.out.println(marksId);
        Marks marks = marksRepository.findById(marksId)
                .orElseThrow(() -> new RuntimeException("Marks not found with ID: " + marksId));

        marks.setMid1(newMid1Marks);

        marksRepository.save(marks);
        return marks;
    }

    public Marks updateMid2Marks(long marksId, float newMid1Marks) {
        System.out.println(marksId);
        Marks marks = marksRepository.findById(marksId)
                .orElseThrow(() -> new RuntimeException("Marks not found with ID: " + marksId));

        marks.setMid2(newMid1Marks);

        marksRepository.save(marks);
        return marks;
    }

    public Marks updateFinalMarks(long marksId, float newMid1Marks) {
        System.out.println(marksId);
        Marks marks = marksRepository.findById(marksId)
                .orElseThrow(() -> new RuntimeException("Marks not found with ID: " + marksId));

        marks.setFinalExam(newMid1Marks);

        marksRepository.save(marks);
        return marks;
    }

    public Marks updateProjectMarks(long marksId, float newMid1Marks) {
        System.out.println(marksId);
        Marks marks = marksRepository.findById(marksId)
                .orElseThrow(() -> new RuntimeException("Marks not found with ID: " + marksId));

        marks.setProject(newMid1Marks);

        marksRepository.save(marks);
        return marks;
    }

}