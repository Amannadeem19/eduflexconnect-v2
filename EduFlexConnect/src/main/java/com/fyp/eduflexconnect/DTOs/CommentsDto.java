package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentsDto {
    private Long id;
    private String msg;
    private LocalDateTime createdAt;
//    private AnnouncementDto studentAnnouncement;
//    private AnnouncementDtoForTeacher teacherAnnouncement;
    private StudentDto commentedBy;
    private  TeacherDto commentedByT;



}
