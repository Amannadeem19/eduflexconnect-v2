package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Job {
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private LocalDateTime createdAt;
        String description, title, location,job_type,companyName;

        @ManyToOne
       SuperAdmin admin;   //nyahan admin add karna ha
        @JsonIgnore
        @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
        private List<JobApplicant> jobApplicants = new ArrayList<>();


}
