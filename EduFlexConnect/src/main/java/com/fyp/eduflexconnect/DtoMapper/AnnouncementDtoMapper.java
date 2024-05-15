package com.fyp.eduflexconnect.DtoMapper;


import com.fyp.eduflexconnect.DTOs.AnnouncementDto;
import com.fyp.eduflexconnect.DTOs.AnnouncementDtos;
import com.fyp.eduflexconnect.DTOs.StudentDto;
import com.fyp.eduflexconnect.DTOs.TeacherDto;
import com.fyp.eduflexconnect.Models.Announcement;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Util.StudentUtil;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementDtoMapper {
    public static AnnouncementDto toAnnouncementDto(Announcement announcement, Student req_student){

        StudentDto studentDto = StudentDtoMapper.toStudentDto(announcement.getStudent());
        AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setId(announcement.getId());
        announcementDto.setContent(announcement.getContent());
        announcementDto.setImage(announcement.getImage());
        announcementDto.setVideo(announcement.getVideo());
        announcementDto.setClassroom_id(announcement.getClassroom().getClassroom_id());
        // adding student which is turned into studentDTO
        announcementDto.setStudentDto(studentDto);
        announcementDto.setReq_user(StudentUtil.isReqUser(req_student, announcement.getStudent()));
        return announcementDto;
        // set comments rehta ha abhi

    }
    public static List<AnnouncementDtos> toGeneralannouncementDtos(List<Announcement> announcementsList){
        List<AnnouncementDtos> announcementDtos = new ArrayList<>();
        for(Announcement announcement : announcementsList) {
            AnnouncementDtos announcementDto = new AnnouncementDtos();
            if(announcement.getTeacher() != null){
                TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(announcement.getTeacher());
                announcementDto.setTeacherDto(teacherDto);

            }
            if (announcement.getStudent() != null){
                StudentDto studentDto = StudentDtoMapper.toStudentDto(announcement.getStudent());
                announcementDto.setStudentDto(studentDto);

            }

            announcementDto.setId(announcement.getId());
            announcementDto.setContent(announcement.getContent());
            announcementDto.setImage(announcement.getImage());
            announcementDto.setVideo(announcement.getVideo());
            announcementDto.setClassroom_id(announcement.getClassroom().getClassroom_id());
            announcementDto.setTotalComments(announcement.getComments().size());
            announcementDtos.add(announcementDto);

        }
        return announcementDtos;

    }

    public static List<AnnouncementDto> toAnnouncementDtos(List<Announcement> announcementsList, Student req_student){
       List<AnnouncementDto> announcementDtos = new ArrayList<>();
       for(Announcement announcement : announcementsList) {
           AnnouncementDto announcementDto = new AnnouncementDto();
//           if(announcement.getTeacher() != null){
//               TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(announcement.getTeacher());
//               announcementDto.set;
//
//           }
           if (announcement.getStudent() != null){
               StudentDto studentDto = StudentDtoMapper.toStudentDto(announcement.getStudent());
               announcementDto.setStudentDto(studentDto);

           }

           announcementDto.setId(announcement.getId());
           announcementDto.setContent(announcement.getContent());
           announcementDto.setImage(announcement.getImage());
           announcementDto.setVideo(announcement.getVideo());
           announcementDto.setClassroom_id(announcement.getClassroom().getClassroom_id());
           // adding student which is turned into studentDTO
           announcementDto.setReq_user(StudentUtil.isReqUser(req_student, announcement.getStudent()));
           announcementDtos.add(announcementDto);
       }
        return announcementDtos;

    }
}
