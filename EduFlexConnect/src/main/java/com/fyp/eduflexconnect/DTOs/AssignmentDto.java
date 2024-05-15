package com.fyp.eduflexconnect.DTOs;

import com.fyp.eduflexconnect.Models.FilesEntity;
import com.fyp.eduflexconnect.Models.Teacher;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AssignmentDto {
    private String title, description;
    private Long id;
    private LocalDateTime createdAt, deadline;
    private TeacherDto teacher;
    private List<FilesEntity> files = new ArrayList<>();




}
