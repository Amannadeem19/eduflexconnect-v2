package com.fyp.eduflexconnect.DtoMapper;


import com.fyp.eduflexconnect.DTOs.AnnouncementDtoForTeacher;
import com.fyp.eduflexconnect.DTOs.TeacherDto;
import com.fyp.eduflexconnect.Models.Announcement;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Util.TeacherUtil;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementDtoMapperTeacher {

    public static AnnouncementDtoForTeacher toAnnouncementDtoForTeacher(Announcement announcement,
                                                                        Teacher req_teacher){
        TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(req_teacher);
        AnnouncementDtoForTeacher announcementDtoForTeacher = new AnnouncementDtoForTeacher();
        announcementDtoForTeacher.setId(announcement.getId());
        announcementDtoForTeacher.setContent(announcement.getContent());
        announcementDtoForTeacher.setImage(announcement.getImage());
        announcementDtoForTeacher.setVideo(announcement.getVideo());
        announcementDtoForTeacher.setClassroom_id(announcement.getClassroom().getClassroom_id());
        //comments dalne ke bad karden ge isse bh
        announcementDtoForTeacher.setTotalComments(0);

        announcementDtoForTeacher.setTeacherDto(teacherDto);
        announcementDtoForTeacher.setReq_user(TeacherUtil.isReqUser(req_teacher,announcement.getTeacher()));
        return announcementDtoForTeacher;
    }
    public static List<AnnouncementDtoForTeacher> toAnnouncementDtoForTeachers(List<Announcement> announcements,
                                                                              Teacher req_teacher){
        List<AnnouncementDtoForTeacher> announcementDtoForTeacherList = new ArrayList<>();

        for (Announcement announcement : announcements) {
            TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(req_teacher);
            AnnouncementDtoForTeacher announcementDtoForTeacher = new AnnouncementDtoForTeacher();
            announcementDtoForTeacher.setId(announcement.getId());
            announcementDtoForTeacher.setContent(announcement.getContent());
            announcementDtoForTeacher.setImage(announcement.getImage());
            announcementDtoForTeacher.setVideo(announcement.getVideo());
            announcementDtoForTeacher.setClassroom_id(announcement.getClassroom().getClassroom_id());


            //comments dalne ke bad karden ge isse bh
            announcementDtoForTeacher.setTotalComments(0);

            announcementDtoForTeacher.setTeacherDto(teacherDto);
            announcementDtoForTeacher.setReq_user(TeacherUtil.isReqUser(req_teacher, announcement.getTeacher()));
            announcementDtoForTeacherList.add(announcementDtoForTeacher);

        }
        return announcementDtoForTeacherList;
    }
}
