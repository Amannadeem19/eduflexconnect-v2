package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Marks;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.CourseService;
import com.fyp.eduflexconnect.Services.MarksService;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Services.TeacherService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import com.fyp.eduflexconnect.Util.MarksUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fyp/marks")
@CrossOrigin("*")
public class MarksController {

    @Autowired
    private MarksService marksService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private  TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;

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
    @GetMapping("/getMid1/{courseId}")
    public List<Object[]> getMid1(@RequestHeader("Authorization") String req_token,
                                  @PathVariable("courseId")Integer courseId
    ) throws UserException {
        System.out.println("check1");
        if(checkRoleTeacher(req_token))
        {
            System.out.println("In controller");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.getMid1MarksTeacher(courseId ,my_teacher);
        }
        else if(checkRoleStudent(req_token))
        {
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Student student = studentService.findStudentProfileByJwt(new_token);
            return marksService.getMid1MarksStudent(courseId,student.getId());
        }
        throw new UserException("You are not authorized to get marks");

    }

    @GetMapping("/getMid2/{courseId}")
    public List<Object[]> getMid2(@RequestHeader("Authorization") String req_token,
                                  @PathVariable("courseId")Integer courseId
    ) throws UserException {
        System.out.println("check1");
        if(checkRoleTeacher(req_token))
        {
            System.out.println("In controller");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.getMid2MarksTeacher(courseId ,my_teacher);
        }
        else if(checkRoleStudent(req_token))
        {
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Student student = studentService.findStudentProfileByJwt(new_token);
            return marksService.getMid2MarksStudent(courseId,student.getId());
        }
        throw new UserException("You are not authorized to get marks");
        //        System.out.println(+marks.getValue());
//        System.out.println(studentId + course + marks);
//        System.out.println(studentId.getClass().getName());
    }

    @GetMapping("/getQuiz/{courseId}")
    public List<Marks> getQuiz(@RequestHeader("Authorization") String req_token,
                               @PathVariable("courseId")Integer courseId
    ) throws UserException {
        System.out.println("check1");
        if (checkRoleTeacher(req_token)) {
            System.out.println("In controller");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.getQuizMarksTeacher(courseId, my_teacher);
        }
        if (checkRoleStudent(req_token)) {
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Student student = studentService.findStudentProfileByJwt(new_token);
            return marksService.getQuizMarksStudent(courseId, student.getId());
        }
        throw new UserException("You are not authorized to get marks");
    }
    //getAssignment
    @GetMapping("/getAssignment/{courseId}")
    public List<Marks> getAssignment(@RequestHeader("Authorization") String req_token,
                                     @PathVariable("courseId")Integer courseId
    ) throws UserException {
        System.out.println("check1");
        if (checkRoleTeacher(req_token)) {
            System.out.println("In controller");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.getAssignmentMarksTeacher(courseId, my_teacher);
        }
        if (checkRoleStudent(req_token)) {
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Student student = studentService.findStudentProfileByJwt(new_token);
            return marksService.getAssignmentMarksStudent(courseId, student.getId());
        }
        throw new UserException("You are not authorized to get marks");
    }

    @GetMapping("/getProject/{courseId}")
    public List<Object[]> getProject(@RequestHeader("Authorization") String req_token,
                                     @PathVariable("courseId")Integer courseId
    ) throws UserException {
        System.out.println("check1");
        if(checkRoleTeacher(req_token))
        {
            System.out.println("In controller");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.getProjectMarksTeacher(courseId ,my_teacher);
        }
        else if(checkRoleStudent(req_token))
        {
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Student student = studentService.findStudentProfileByJwt(new_token);
            return marksService.getProjectMarksStudent(courseId,student.getId());
        }
        throw new UserException("You are not authorized to get marks");
    }
    @GetMapping("/getFinal/{courseId}")
    public List<Object[]> getFinal(@RequestHeader("Authorization") String req_token,
                                   @PathVariable("courseId")Integer courseId
    ) throws UserException {
        System.out.println("check1");
        if (checkRoleTeacher(req_token)) {
            System.out.println("In controller");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.getFinalMarksTeacher(courseId, my_teacher);
        } else if (checkRoleStudent(req_token)) {
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Student student = studentService.findStudentProfileByJwt(new_token);
            return marksService.getFinalMarksStudent(courseId, student.getId());
        }
        throw new UserException("You are not authorized to get marks");
    }
    @PostMapping("/addMid1/{studentId}/{courseId}")
    public Marks addMid1(@RequestHeader("Authorization") String req_token,
                         @PathVariable("studentId")String studentId , @PathVariable("courseId")Integer courseId,
                         @RequestBody MarksUtills marks ) throws UserException {
        if(checkRoleTeacher(req_token))
        {
            System.out.println("test2");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.addMid1(studentId , courseId ,my_teacher, marks.getValue() , marks.getTotValue());
        }
        throw new UserException("You are not authorized to add marks");
    }
    @PostMapping("/addMid2/{studentId}/{courseId}")
    public Marks addMid2(@RequestHeader("Authorization") String req_token,
                         @PathVariable("studentId")String studentId , @PathVariable("courseId")Integer courseId,
                         @RequestBody MarksUtills marks ) throws UserException {
        if(checkRoleTeacher(req_token))
        {
            System.out.println("test2");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.addMid2(studentId , courseId ,my_teacher, marks.getValue(), marks.getTotValue());
        }
        throw new UserException("You are not authorized to add marks");
    }

    @PostMapping("/addProject/{studentId}/{courseId}")
    public Marks addProject(@RequestHeader("Authorization") String req_token,
                            @PathVariable("studentId")String studentId , @PathVariable("courseId")Integer courseId,
                            @RequestBody MarksUtills marks ) throws UserException {
        if(checkRoleTeacher(req_token))
        {
            System.out.println("test2");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.addProject(studentId , courseId ,my_teacher, marks.getValue(), marks.getTotValue());
        }
        throw new UserException("You are not authorized to add marks");

    }

    @PostMapping("/addQuiz/{studentId}/{courseId}/{quizId}")
    public Marks addQuiz(@RequestHeader("Authorization") String req_token,
                         @PathVariable("studentId")String studentId , @PathVariable("courseId")Integer courseId,
                         @RequestBody MarksUtills marks ,@PathVariable("quizId")int quizId)
            throws UserException {
        if(checkRoleTeacher(req_token))
        {
            System.out.println("test2");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.addQuiz(studentId , courseId ,my_teacher, marks.getValue(),quizId, marks.getTotValue());
        }
        throw new UserException("You are not authorized to add marks");
    }
    @PostMapping("/addFinalExam/{studentId}/{courseId}")
    public Marks addFinalExam(@RequestHeader("Authorization") String req_token,
                              @PathVariable("studentId")String studentId , @PathVariable("courseId")Integer courseId,
                              @RequestBody MarksUtills marks ) throws UserException {
        if(checkRoleTeacher(req_token))
        {
            System.out.println("test2");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.addFinalExam(studentId , courseId ,my_teacher, marks.getValue(), marks.getTotValue());
        }
        throw new UserException("You are not authorized to add marks");

    }
    @PostMapping("/addAssignment/{studentId}/{courseId}/{assignmentId}")
    public Marks addAssignment(@RequestHeader("Authorization") String req_token,
                               @PathVariable("studentId")String studentId , @PathVariable("courseId")Integer courseId,
                               @RequestBody MarksUtills marks ,@PathVariable("assignmentId")int assignmentId)
            throws UserException {
        if(checkRoleTeacher(req_token))
        {
            System.out.println("test2");
            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.addAssignment(studentId , courseId ,my_teacher, marks.getValue(),assignmentId, marks.getTotValue());
        }
        throw new UserException("You are not authorized to add marks");

    }
    @PutMapping("/updateMid1/{marksId}")
    public Marks updateMid1(@RequestHeader("Authorization") String req_token,
                            @PathVariable("marksId")Long marksId ,
                            @RequestBody MarksUtills marks ) throws UserException {

        if(checkRoleTeacher(req_token))
        {
            System.out.println("InController");
//            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
//            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.updateMid1Marks(marksId,marks.getValue());
        }
        throw new UserException("You are not authorized to update marks");

    }

    @PutMapping("/updateMid2/{marksId}")
    public Marks updateMid2(@RequestHeader("Authorization") String req_token,
                            @PathVariable("marksId")Long marksId ,
                            @RequestBody MarksUtills marks ) throws UserException {

        if(checkRoleTeacher(req_token))
        {
            System.out.println("InController");
//            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
//            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.updateMid2Marks(marksId,marks.getValue());
        }
        throw new UserException("You are not authorized to update marks");

    }

    @PutMapping("/updateFinal/{marksId}")
    public Marks updateFinal(@RequestHeader("Authorization") String req_token,
                             @PathVariable("marksId")Long marksId ,
                             @RequestBody MarksUtills marks ) throws UserException {

        if(checkRoleTeacher(req_token))
        {
            System.out.println("InController");
//            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
//            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.updateFinalMarks(marksId,marks.getValue());
        }
        throw new UserException("You are not authorized to update marks");

    }

    @PutMapping("/updateProject/{marksId}")
    public Marks updateProject(@RequestHeader("Authorization") String req_token,
                               @PathVariable("marksId")Long marksId ,
                               @RequestBody MarksUtills marks ) throws UserException {

        if(checkRoleTeacher(req_token))
        {
            System.out.println("InController");
//            String new_token = COMMONFuncs.separateBearerFromToken(req_token);
//            Teacher my_teacher = teacherService.findTeacherProfileByJwt(new_token);
            return marksService.updateProjectMarks(marksId,marks.getValue());
        }
        throw new UserException("You are not authorized to update marks");

    }


}