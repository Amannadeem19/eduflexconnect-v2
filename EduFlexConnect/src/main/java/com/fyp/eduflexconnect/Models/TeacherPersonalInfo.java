package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherPersonalInfo
{
    @Id
    private String username;
    private String first_name;
    private String last_name;
    private String gender;
    private LocalDate dob;

    @Column(unique = true)
    private String cnic;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String mobile;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "username")
    @JsonBackReference(value = "teacher-personal")
    private Teacher teacher;


}
