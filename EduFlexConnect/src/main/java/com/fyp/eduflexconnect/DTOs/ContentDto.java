package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class ContentDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private TeacherDto teacher;
    private List<FilesDto> files = new ArrayList<>();
    private int totalFiles;
    private boolean isUpdated;
}
