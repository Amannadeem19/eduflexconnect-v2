package com.fyp.CourseRegistration.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StudentPersonalInfo
{
    @Id
    private String id;
    private String full_name;
    private String gender;
    private LocalDate dob;
    private String cnic;
    private String email;
    private String mobile;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private Student student;

}
