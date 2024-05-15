package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.Config.TimetableConfig;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Models.Timetable;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Services.TeacherService;
import com.fyp.eduflexconnect.Services.TimetableService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("fyp/timetable")
@CrossOrigin("*")
public class TimetableController
{
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private boolean checkRoleTeacher(String req_token) throws UserException {
//        System.out.println(req_token);
    String new_token = COMMONFuncs.separateBearerFromToken(req_token);
//        System.out.println(new_token);
    List<String> roles = jwtTokenProvider.findRole(new_token);
    if(roles.contains("Role_TEACHER")){

        return true;
    }
    return false;
}
    private boolean checkRoleStudent(String req_token)
    {
        String new_token = COMMONFuncs.separateBearerFromToken(req_token);
        System.out.println(new_token);
        List<String> roles = jwtTokenProvider.findRole(new_token);
        if(roles.contains("Role_STUDENT")){

            return true;
        }
        return false;
    }
    @Autowired
    private TimetableService timetable2Ser;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestHeader("Authorization") String req_token,@RequestParam("file") MultipartFile file ) throws IOException, UserException{
        if(checkRoleTeacher(req_token))
        {
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            String teacherName = my_teacher.getUsername();
            System.out.println("teacherName in timetable"+teacherName);
            if(TimetableConfig.checkExcelFormat(file))
            {
                this.timetable2Ser.deleteTimeTable();
                this.timetable2Ser.saveTeacher(file,teacherName);
                return ResponseEntity.ok(Map.of("message","File uploaded successfully"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Do upload excel file");
        }
        else if(checkRoleStudent(req_token))
        {
            String token = req_token.substring(7);
            String student_id = jwtTokenProvider.getUsernameFromToken(token);
            Student student = studentService.findStudentByUsername(student_id);
            String studentSection = student.getSection().getId();
//            System.out.println("studentSection in timetable"+studentSection);
            if(TimetableConfig.checkExcelFormat(file))
            {
                this.timetable2Ser.deleteTimeTable();
                this.timetable2Ser.saveStudent(file,studentSection);
                return ResponseEntity.ok(Map.of("message","File uploaded successfully"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Do upload excel file");
        }
        throw new UserException("You are not authorized to get marks");

    }
    @GetMapping
    public List<Timetable> gettimetable()
    {
        return this.timetable2Ser.Timetable();
    }
}

