package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Data
public class Student
{
    @Id
    private String id;
    private String degree;
    private String batch;
    private String campus;
    private String status;
    private String login_password;

    //Added
    private  boolean req_user;
    private String image;
    //End

    @ManyToOne
    @JoinColumn(name = "section_id")
    @JsonIgnore
    private Section section;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private StudentContactInfo contact_info;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private StudentPersonalInfo personal_info;

    //Added
   
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Announcement> announcements = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Classroom> classrooms = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ElectiveClassroom> electiveClassrooms = new ArrayList<>();
    //END
    @ManyToMany
    @JoinTable(
            name = "student_assignments",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "assign_id")
    )
    @JsonIgnore
    private List<Assignment> assignments = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LectureFeedback> feedback;


    public Student() {
    }

    public Student(String id, String degree, String batch, String campus, String status, String login_password, boolean req_user, String image, Section section, StudentContactInfo contact_info, StudentPersonalInfo personal_info, List<Announcement> announcements, List<Comment> comments, List<Classroom> classrooms, List<ElectiveClassroom> electiveClassrooms, List<Assignment> assignments, List<Submission> submissions, List<LectureFeedback> feedback) {
        this.id = id;
        this.degree = degree;
        this.batch = batch;
        this.campus = campus;
        this.status = status;
        this.login_password = login_password;
        this.req_user = req_user;
        this.image = image;
        this.section = section;
        this.contact_info = contact_info;
        this.personal_info = personal_info;
        this.announcements = announcements;
        this.comments = comments;
        this.classrooms = classrooms;
        this.electiveClassrooms = electiveClassrooms;
        this.assignments = assignments;
        this.submissions = submissions;
        this.feedback = feedback;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }


    public void setCampus(String campus) {
        this.campus = campus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public void setContact_info(StudentContactInfo contact_info) {
        this.contact_info = contact_info;
    }

    public void setPersonal_info(StudentPersonalInfo personal_info) {
        this.personal_info = personal_info;
    }

    public void setReq_user(boolean req_user) {
        this.req_user = req_user;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
