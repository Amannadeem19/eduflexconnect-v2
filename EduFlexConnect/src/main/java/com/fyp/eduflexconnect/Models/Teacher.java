package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(generator = "teacher_username_generator")
    @GenericGenerator(name = "teacher_username_generator", strategy = "com.fyp.eduflexconnect.Generators.TeacherUsernameGenerator")
    private String username;
    private String qualification;
    private String major;

    private String campus;

    private String login_password;

    //ADDED
    private String bio, image, experience;
    //END

    @OneToOne(mappedBy = "teacher",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference(value = "teacher-personal")
    private TeacherPersonalInfo personal_info;

    @OneToOne(mappedBy = "teacher",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference(value = "teacher-contact")
    private TeacherContactInfo contact_info;

    @JsonManagedReference
    @JsonIgnore
    @ManyToMany(mappedBy = "teacher" , cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    //ADDED
    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Announcement> announcements = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    //    private ArrayList<String> coursePreferences;
    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Content> contentList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<LectureFeedback> teacherFeedbacks = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Lecture>lectures = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private  List<Assignment> assignments = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Classroom> section  = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ElectiveClassroom> electiveClassrooms  = new ArrayList<>();



}
