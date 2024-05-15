package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Scholarship {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String title, description,provider, eligibilityCriteria, deadline;
        private LocalDateTime createdAt;

        @ManyToOne
        SuperAdmin admin; // yahan admin entity dalni ha

}
