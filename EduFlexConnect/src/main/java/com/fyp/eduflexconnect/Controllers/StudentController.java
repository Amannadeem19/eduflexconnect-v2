package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.DTOs.StudentDto;
import com.fyp.eduflexconnect.DtoMapper.StudentDtoMapper;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Section;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Util.StudentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/students")
@RestController
public class StudentController
{
        @Autowired
        StudentService studentService;

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @GetMapping("/profile")
        public ResponseEntity<StudentDto> getStudentProfile(@RequestHeader("Authorization") String jwt)throws UserException
        {

                String tokenWithoutBearer = jwt.substring(7);
                Student student = studentService.findStudentProfileByJwt(tokenWithoutBearer);

                StudentDto studentDto = StudentDtoMapper.toStudentDto(student);
                studentDto.setReq_user(true);
                return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
        }

        @GetMapping("/profile/{username}")
        public ResponseEntity<StudentDto>getStudentProfileByUsername(@PathVariable String username, @RequestHeader("Authorization") String jwt) throws UserException
        {
                String tokenWithOutBearer = jwt.substring(7);
                Student req_student = studentService.findStudentProfileByJwt(tokenWithOutBearer);
                Student studentProfile = studentService.findStudentByUsername(username);
                StudentDto studentDto = StudentDtoMapper.toStudentDto(studentProfile);
                studentDto.setReq_user(StudentUtil.isReqUser(req_student, studentProfile));
                return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
        }
        @GetMapping("/{stud_id}")
        public ResponseEntity<StudentDto> getStudentById(@PathVariable String id, @RequestHeader("Authorization")String jwt) throws UserException
        {
                String token = jwt.substring(7);
                Student req_student = studentService.findStudentProfileByJwt(token);
                Student student = studentService.findStudentByUsername(id);
                StudentDto studentDto = StudentDtoMapper.toStudentDto(student);
                studentDto.setReq_user(StudentUtil.isReqUser(req_student, student));
                return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
        }

        @GetMapping("/search")
        public ResponseEntity<List<StudentDto>> searchStudents(@RequestParam String query,
                                                               @RequestHeader("Authorization")String jwt)
                throws UserException
        {
               String token  = jwt.substring(7);
                Student req_Student = studentService.findStudentProfileByJwt(token);
                List<Student> findStudents = studentService.searchStudents(query);
                List<StudentDto> studentDtos = StudentDtoMapper.studentDtos(findStudents);
                System.out.println("search students" + studentDtos);
                return new ResponseEntity<>(studentDtos, HttpStatus.ACCEPTED);
                //payload me student id ho zaror
                // error arah idhr
        }


        @PutMapping("/update")
        public ResponseEntity<StudentDto> updateStudent(@RequestBody Student req_student,
                                                        @RequestHeader("Authorization")String jwt)
                throws UserException{
                System.out.println("requested Student" + req_student);
                String token  = jwt.substring(7);
                Student student = studentService.findStudentProfileByJwt(token);
                System.out.println("update student"+student);
                Student updatedStudent = studentService.updateStudent(req_student, student.getId());
                StudentDto studentDto = StudentDtoMapper.toStudentDto(updatedStudent);
                return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
        }

        @GetMapping("/getStudent/{stud_id}")
        public Student getStudent(@PathVariable String stud_id) throws UserException
        {

                return  studentService.findStudentByUsername(stud_id);

        }
        @GetMapping("/getStudentSection/{stud_id}")
        public Section getStudentSection(@PathVariable String stud_id) throws UserException
        {

                return  studentService.getSectionOfStudent(stud_id);

        }



}
