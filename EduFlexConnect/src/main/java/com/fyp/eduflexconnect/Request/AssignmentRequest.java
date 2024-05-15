package com.fyp.eduflexconnect.Request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentRequest {
    private String title, description;
    private LocalDateTime deadline;


}
