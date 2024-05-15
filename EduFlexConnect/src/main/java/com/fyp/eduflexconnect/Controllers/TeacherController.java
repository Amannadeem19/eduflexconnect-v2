package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.DTOs.AttendanceDTO;
import com.fyp.eduflexconnect.DTOs.TeacherDto;
import com.fyp.eduflexconnect.DtoMapper.TeacherDtoMapper;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Models.TeacherCourse;
import com.fyp.eduflexconnect.Request.AttendanceRecord;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.*;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import com.fyp.eduflexconnect.Util.TeacherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ClassroomController classroomController;
    @Autowired
    private AttendanceService attendanceService;

    //test karna ha
    @GetMapping("/profile")
    public Teacher getTeacherProfile(@RequestHeader("Authorization")String jwt) throws UserException
    {
        String token = jwt.substring(7);
        Teacher teacher = teacherService.findTeacherProfileByJwt(token);
        // dto me bh convert karna ha abhi
        return teacher;

    }
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherByUsername(@PathVariable String username, @RequestHeader("Authorization")String jwt)
            throws UserException{
        String token  = COMMONFuncs.separateBearerFromToken(jwt);
        Teacher req_teacher = teacherService.findTeacherProfileByJwt(token);
        Teacher teacher = teacherService.findTeacherByUsername(username);
        System.out.println(teacher);
        TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(teacher);
        teacherDto.setReq_user(TeacherUtil.isReqUser(req_teacher, teacher));
    return new ResponseEntity<>(teacherDto, HttpStatus.ACCEPTED);
    }
    @PostMapping("/addteacher")
    public Teacher addteacher(@RequestBody Teacher teacher) throws UserException
    {
        return teacherService.AddTeacher(teacher);
    }

    @GetMapping("/getallteachers")
    public List<Teacher> getallteachers()
    {
        return teacherService.getAllTeachers();
    }
    @GetMapping("/getteacher/{username}")
    public Teacher getteacher(@PathVariable String username)
    {

        return teacherService.getTeacher(username);
    }

    @GetMapping("/currentcourse")
    public List<TeacherCourse> CurrentCourse(@RequestHeader("Authorization") String authorizationHeader) throws UserException {

        // Bearer ko remove kia he token ke start se.
        String token = authorizationHeader.substring(7);
        Teacher teacher = teacherService.findTeacherProfileByJwt(token);
        String semester_id = semesterService.currentSemester();


        return teacherCourseService.teachercurrentcourse(teacher,semester_id);
    }

    @GetMapping("/listofstudent/{classId}/{courseCode}")
    public List<Student> ListOfStudents(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String classId, @PathVariable String courseCode) throws UserException {

        // Bearer ko remove kia he token ke start se.
        String token = authorizationHeader.substring(7);
        Teacher teacher = teacherService.findTeacherProfileByJwt(token);
        Course course = courseService.GetCourse(courseCode);
        if(course.getType().equals("Core"))
        {
            return classroomController.getClassroomStudents(classId);
        }
        else
        {
            return classroomController.getElectiveClassroomStudents(Integer.parseInt(classId));
        }

    }

    @GetMapping("/loadattendance/{teacher_id}/{semester_id}/{course_code}/{section_id}")
    public List<AttendanceDTO> loadAttendance(@PathVariable String teacher_id,@PathVariable String semester_id,@PathVariable String course_code,@PathVariable String section_id)
    {
        return attendanceService.teacherloadAttendance(teacher_id,semester_id,course_code,section_id);
    }
    @PostMapping("/markattendance")
    public String markAttendance(@RequestBody AttendanceRecord attendanceRecord)
    {
        return attendanceService.markAttendance(attendanceRecord.getDate(),attendanceRecord.getCourseCode(),attendanceRecord.getSemesterId(),attendanceRecord.getAttendanceReq(),attendanceRecord.getTeacherId());
    }
    @PostMapping("/updateattendance")
    public String updateAttendance(@RequestBody  AttendanceRecord attendanceRecord)
    {
        return attendanceService.updateAttendance(attendanceRecord.getDate(),attendanceRecord.getCourseCode(),attendanceRecord.getSemesterId(),attendanceRecord.getAttendanceReq(),attendanceRecord.getTeacherId());
    }





}
