package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

@Data
public class AnnouncementDtos {
    private Long id;
    private String content, image, video;
    private TeacherDto teacherDto;
    private StudentDto studentDto;
    int totalComments;
    private String classroom_id;
}
