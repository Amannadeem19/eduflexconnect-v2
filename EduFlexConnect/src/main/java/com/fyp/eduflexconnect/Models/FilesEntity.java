package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String name;
    private String type;
    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;
    @OneToOne
    @JoinColumn(name = "job_applicant_id")
    private JobApplicant jobApplicant;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "assign_id")
    private Assignment assignment;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private Student student;
}


