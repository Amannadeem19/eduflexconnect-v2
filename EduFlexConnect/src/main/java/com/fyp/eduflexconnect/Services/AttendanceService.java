package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.DTOs.AttendanceDTO;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.*;
import com.fyp.eduflexconnect.Request.AttendanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService
{
    @Autowired
    private AttendanceRepository attendance_repo;
    @Autowired
    private TeacherRepository teacher_repo;
    @Autowired
    private SemesterRepository semester_repo;
    @Autowired
    private CourseRepository course_repo;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentRepository student_repo;


    public List<AttendanceDTO> teacherloadAttendance(String teacher_id, String semester_id, String course_code, String section_id)
    {
        List<AttendanceDTO> attendanceList = new ArrayList<>();
        Teacher teacher = teacher_repo.findById(teacher_id).orElse(null);
        Semester semester = semester_repo.findById(semester_id).orElse(null);
        Course course = course_repo.findByCode(course_code);

        List<Enrollment> enrollments = enrollmentService.getSectionEnrollments(course,semester,section_id);
        List<Student> students = enrollments.stream().map(Enrollment::getStudent).toList();

        for(Student s: students)
        {
            AttendanceDTO attendance = new AttendanceDTO();
            attendance.setStudent(s);
            attendance.setAttendanceList(attendance_repo.findByStudentAndCourseAndSemester(s,course,semester));
            attendanceList.add(attendance);
        }
        return attendanceList;
    }



    public String markAttendance(LocalDate date, String course_code, String semester_id, List<AttendanceRequest> attendance_req, String teacher_id)
    {
        System.out.println(date+course_code+semester_id+attendance_req+teacher_id);
        Course course = course_repo.findByCode(course_code);

        Semester semester = semester_repo.findById(semester_id).orElse(null);

        Teacher teacher = teacher_repo.findById(teacher_id).orElse(null);


        for(AttendanceRequest single_req: attendance_req)
        {
            Attendance attendance = new Attendance();
            attendance.setCourse(course);
            attendance.setSemester(semester);
            attendance.setStudent(student_repo.findById(single_req.getStudent_id()).orElse(null));
            attendance.setTeacher(teacher);
            attendance.setDate(date);
            attendance.setStatus(single_req.getStatus());
            attendance_repo.save(attendance);
        }
        return "Success";
    }

    public String updateAttendance(LocalDate date, String course_code, String semester_id, List<AttendanceRequest> attendance_req, String teacher_id)
    {
        Course course = course_repo.findByCode(course_code);
        Semester semester = semester_repo.findById(semester_id).orElse(null);
        Teacher teacher = teacher_repo.findById(teacher_id).orElse(null);

        for(AttendanceRequest single_req: attendance_req)
        {
            Student student = student_repo.findById(single_req.getStudent_id()).orElse(null);
            Attendance existing_attendance = attendance_repo.findByCourseAndSemesterAndStudentAndDate(course,semester,student,date);
            existing_attendance.setStatus(single_req.getStatus());
            attendance_repo.save(existing_attendance);

        }
        return "Success";
    }

    public List<Attendance> getStudentAttendanceCourse(String studentId, String courseCode, String semesterId)
    {
        Course course = course_repo.findByCode(courseCode);
        Semester semester = semester_repo.findById(semesterId).orElse(null);
        Student student = student_repo.findById(studentId).orElse(null);

        return attendance_repo.findByStudentAndCourseAndSemester(student,course,semester);

    }
}
