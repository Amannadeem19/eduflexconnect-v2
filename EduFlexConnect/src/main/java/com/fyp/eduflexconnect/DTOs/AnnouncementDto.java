package com.fyp.eduflexconnect.DTOs;

import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class AnnouncementDto
{
    private Long id;
    private String content, image, video;
    private StudentDto studentDto;
    private boolean req_user;
    int totalComments;
    private String classroom_id;

}
