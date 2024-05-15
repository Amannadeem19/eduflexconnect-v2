package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobApplicant {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String email, username;

        @ManyToOne
        @JoinColumn(name = "job_id")
        private Job job;

        @OneToOne(mappedBy = "jobApplicant", cascade = CascadeType.ALL)
        private FilesEntity filesEntity;

}
//        @Lob
//        @Column(name = "file_data", columnDefinition = "LONGBLOB")
//        private byte[] resume;

