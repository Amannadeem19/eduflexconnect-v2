package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentDtoTeacher {
    private Long id;
    private String msg;
    private LocalDateTime createdAt;
    private AnnouncementDto studAnnouncement;
    private AnnouncementDtoForTeacher teacherAnnouncement;
    private TeacherDto commentedBy;
    boolean isReqUser;
}
