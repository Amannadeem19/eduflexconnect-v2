package com.fyp.eduflexconnect.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Semester {
    @Id
    @GeneratedValue(generator = "semester_id_generator")
    @GenericGenerator(name = "semester_id_generator", strategy = "com.fyp.eduflexconnect.Generators.SemesterIdGenerator")
    private String semester_id;
    private String name;
    private int year;
    private LocalDate start_date;
    private LocalDate end_date;
    private String status;
    private boolean isRegistrationOpen;

    @OneToMany(mappedBy = "semester")
    @JsonIgnore
    private List<OfferedCourse> offeredCourses;
    @OneToMany
    @JsonIgnore
    private List<Classroom> classrooms;

    @OneToMany
    @JsonIgnore
    private List<ElectiveClassroom> electiveClassrooms;




}
