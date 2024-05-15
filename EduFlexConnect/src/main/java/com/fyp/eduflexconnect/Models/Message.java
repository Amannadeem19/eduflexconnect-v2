package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Chat chat;
}
