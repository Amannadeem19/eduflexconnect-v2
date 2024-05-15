package com.fyp.eduflexconnect.DTOs;

import com.fyp.eduflexconnect.Models.Assignment;
import com.fyp.eduflexconnect.Models.Student;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionDto {

    private Long assignId;
    private Long id;
    private String file_name;
    private String file_type;
    private LocalDateTime deadline, submissionTime;
    private String status;
    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB")
    private byte[] fileData;
    private StudentDto student;
}
