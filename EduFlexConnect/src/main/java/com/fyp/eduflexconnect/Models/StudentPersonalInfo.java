package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
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

    public StudentPersonalInfo(String id, String full_name, String gender, LocalDate dob, String cnic, String email, String mobile, Student student) {
        this.id = id;
        this.full_name = full_name;
        this.gender = gender;
        this.dob = dob;
        this.cnic = cnic;
        this.email = email;
        this.mobile = mobile;
        this.student = student;
    }

    public StudentPersonalInfo() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
