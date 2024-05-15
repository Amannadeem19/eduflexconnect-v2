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
public class Classroom
{
    @Id
    @GeneratedValue(generator = "classroom_id_generator")
    @GenericGenerator(name = "classroom_id_generator",strategy = "com.fyp.eduflexconnect.Generators.ClassIDGenerator")
    private String classroom_id;
    @ManyToOne
    @JoinColumn(name = "id")
    @JsonManagedReference
    private Section section;
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonManagedReference
    private Course course;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    @JsonManagedReference(value = "classroom-semester")
    private Semester semester;
    @ManyToMany(mappedBy = "classrooms")
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Announcement> announcements = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Content> contents = new ArrayList<>();

    @ManyToOne
    @JsonManagedReference
    private Teacher teacher;


}
