package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

@Data
public class AnnouncementDtoForTeacher {

    private Long id;
    private String content, image, video;
    private TeacherDto teacherDto;
    private boolean req_user;
    int totalComments;

    private String classroom_id;

}
