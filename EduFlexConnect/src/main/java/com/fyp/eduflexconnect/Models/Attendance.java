package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Attendance
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Course course;
    @ManyToOne
    private Semester semester;
    private LocalDate date;
    private String status;
}
