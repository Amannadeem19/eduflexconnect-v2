package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String type;
    private LocalDateTime deadline, submissionTime;
    private String status;
    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @ManyToOne
    private Assignment assignment;
    @ManyToOne
    private Student student;



}
