package com.fyp.eduflexconnect.Controllers;

;
import com.fyp.eduflexconnect.DTOs.AnnouncementDto;
import com.fyp.eduflexconnect.DTOs.AnnouncementDtoForTeacher;
import com.fyp.eduflexconnect.DTOs.AnnouncementDtos;
import com.fyp.eduflexconnect.DtoMapper.AnnouncementDtoMapper;
import com.fyp.eduflexconnect.DtoMapper.AnnouncementDtoMapperTeacher;
import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Announcement;
import com.fyp.eduflexconnect.Models.Classroom;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.AnnouncementService;
import com.fyp.eduflexconnect.Services.ClassroomService;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Services.TeacherService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
    private Announcement my_announcement;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/create/{classroomId}")
    public ResponseEntity<AnnouncementResponse<?>>createAnnouncement(@RequestBody Announcement announcement,
                                              @RequestHeader("Authorization")String jwt,
                                                                     @PathVariable("classroomId")String classroom_id)
        throws AnnouncementException, UserException
    {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean checkRole = jwtTokenProvider.getAuthorities(token);
        Classroom classroom = classroomService.findClassroomById(classroom_id);
        if(checkRole){
            System.out.println("You are student");
            Student student = studentService.findStudentProfileByJwt(token);
            Announcement makeAnnouncement = announcementService.
                    createAnnouncementForStudent(announcement, student, classroom);
            AnnouncementDto announcementDto= AnnouncementDtoMapper.toAnnouncementDto(makeAnnouncement, student);
            AnnouncementResponse<AnnouncementDto> announcementResponse = new AnnouncementResponse<>();
            announcementResponse.setAnnouncement(announcementDto);
            announcementResponse.setType("student");
            return new ResponseEntity<>(announcementResponse, HttpStatus.CREATED);

        }
        // for teacher
        System.out.println("you are teacher");
        Teacher teacher = teacherService.findTeacherProfileByJwt(token);
        Announcement makeAnnouncement = announcementService.createAnnouncementForTeacher(announcement, teacher, classroom);
        AnnouncementDtoForTeacher announcementDtoForTeacher = AnnouncementDtoMapperTeacher
                            .toAnnouncementDtoForTeacher(makeAnnouncement, teacher);
        AnnouncementResponse<AnnouncementDtoForTeacher> announcementResponse = new AnnouncementResponse<>();
        announcementResponse.setType("teacher");
        announcementResponse.setAnnouncement(announcementDtoForTeacher);
      return new ResponseEntity<>(announcementResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{classroomId}")
    public ResponseEntity<List<AnnouncementDtos>> findAllAnnouncements(@PathVariable("classroomId")String classroomId)
    throws UserException
    {
        Classroom classroom = classroomService.findClassroomById(classroomId);

//        String token = COMMONFuncs.separateBearerFromToken(jwt);
//        Student student = studentService.findStudentProfileByJwt(token);
        List<Announcement> announcementsList = announcementService.findAllAnnouncementsInDescendingOrder(classroom);
        List<AnnouncementDtos> announcementDtos = AnnouncementDtoMapper.toGeneralannouncementDtos(announcementsList);
        return new ResponseEntity<>(announcementDtos, HttpStatus.ACCEPTED);
    }

    @GetMapping("/announce/{announce_id}")
    public ResponseEntity<AnnouncementResponse<?>> FindByIdAnnouncement(@PathVariable Long announce_id,
                                                              @RequestHeader("Authorization") String jwt)
            throws UserException, AnnouncementException
    {
        String token = jwt.substring(7);
        boolean checkRole = jwtTokenProvider.getAuthorities(token);
        if(checkRole){
            System.out.println("you are student");
            Student student = studentService.findStudentProfileByJwt(token);
            Announcement announcement= announcementService.findById(announce_id);
            AnnouncementDto announcementDto = AnnouncementDtoMapper.toAnnouncementDto(announcement, student);
           AnnouncementResponse<AnnouncementDto> announcementResponse = new AnnouncementResponse<>();
           announcementResponse.setType("student");
           announcementResponse.setAnnouncement(announcementDto);
            return new ResponseEntity<>(announcementResponse, HttpStatus.ACCEPTED);
        }
//        teacher ho agar tw
        System.out.println("you are teacher");
        Teacher teacher = teacherService.findTeacherProfileByJwt(token);
        Announcement announcement= announcementService.findById(announce_id);
        AnnouncementDtoForTeacher announcementDtoForTeacher = AnnouncementDtoMapperTeacher.
                                toAnnouncementDtoForTeacher(announcement, teacher);
        AnnouncementResponse<AnnouncementDtoForTeacher> announcementResponse = new AnnouncementResponse<>();
        announcementResponse.setType("teacher");
        announcementResponse.setAnnouncement(announcementDtoForTeacher);
        return new ResponseEntity<>(announcementResponse, HttpStatus.ACCEPTED);

    }
    @DeleteMapping("/deleted/{announce_id}")
    public ResponseEntity<ApiResponse> deleteAnnouncement(@PathVariable Long announce_id,
                                                          @RequestHeader("Authorization") String jwt)
   throws UserException, AnnouncementException
    {
        String token = jwt.substring(7);
        boolean checkRole = jwtTokenProvider.getAuthorities(token);
        if (checkRole){
            Student student = studentService.findStudentProfileByJwt(token);
            announcementService.deleteStudentAnnouncementById(announce_id, student.getId());
        }
        else{
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            announcementService.deleteTeacherAnnouncementById(announce_id, teacher.getUsername());
        }

        ApiResponse apiResponse = new ApiResponse("Announcement Deleted Successfully ", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("/update/{announce_id}")
    public ResponseEntity<AnnouncementResponse<?>> updateAnnouncementForStudent(@RequestBody Announcement req_announcement,
                                                                        @PathVariable Long announce_id,
                                                                                @RequestHeader("Authorization") String jwt )
  throws UserException, AnnouncementException{
        String token = jwt.substring(7);
        boolean checkRole = jwtTokenProvider.getAuthorities(token);
        if (checkRole){
            System.out.println("you are student");
            Student student = studentService.findStudentProfileByJwt(token);
            Announcement announcement = announcementService.
                    updateStudentAnnouncementById(req_announcement,announce_id, student.getId());
            AnnouncementDto announcementDto = AnnouncementDtoMapper.toAnnouncementDto(announcement, student);
            AnnouncementResponse<AnnouncementDto> announcementResponse = new AnnouncementResponse<>();
            announcementResponse.setType("student");
            announcementResponse.setUpdated(true);
            announcementResponse.setAnnouncement(announcementDto);
            return new ResponseEntity<>(announcementResponse, HttpStatus.ACCEPTED);

        }
        System.out.println("you are teacher");
        Teacher teacher = teacherService.findTeacherProfileByJwt(token);
        Announcement announcement = announcementService.
                updateTeacherAnnouncementById(req_announcement,announce_id, teacher.getUsername());
        AnnouncementDtoForTeacher announcementDtoForTeacher = AnnouncementDtoMapperTeacher.
                            toAnnouncementDtoForTeacher(announcement, teacher);
        AnnouncementResponse<AnnouncementDtoForTeacher> announcementResponse = new AnnouncementResponse<>();
        announcementResponse.setType("teacher");
        announcementResponse.setUpdated(true);
        announcementResponse.setAnnouncement(announcementDtoForTeacher);
        return new ResponseEntity<>(announcementResponse, HttpStatus.ACCEPTED);


    }

}
